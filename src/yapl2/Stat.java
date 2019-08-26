package yapl2;

import java.io.PrintWriter;
import java.util.List;
import javax.xml.stream.XMLStreamWriter;

import analizzatoresemantico.Env;
import toolmanutenzione.*;
import yapl2.Expr.Identifier;
import yapl2.Expr.Type;

//collaudata
public abstract class Stat implements AzioniCompilatore{

	// nodo readOp
	public static class ReadOP extends Stat {
		private List<Identifier> id;
		private List<Type> tipo;
		private String type;

		public ReadOP(List<Identifier> id, List<Type> tipo) {
			this.id = id;
			this.tipo = tipo;
			this.type = null;
		}

		public String toString() {
			String frase = "";
			String frase2 = "";
			frase = id.get(0) + frase;
			frase2 = tipo.get(0) + frase2;
			for (int i = 1; i < id.size(); i++) {
				frase = id.get(i) + "," + frase;
			}
			for (int i = 1; i < tipo.size(); i++) {
				frase2 = tipo.get(i) + "," + frase2;
			}
			return frase + " <-" + frase2 + "\n";
		}

		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			x.writeStartElement("READOP");
			if (type != null) {
				x.writeAttribute("TYPE", type);
			}
			for (int i = id.size() - 1; i >= 0; i--) {
				Identifier g = id.get(i);
				g.drawComponent(x);
			}
			for (int i = tipo.size() - 1; i >= 0; i--) {
				Type g = tipo.get(i);
				g.drawComponent(x);
			}
			x.writeEndElement();
		}

		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			c.write("	scanf(\"");
			for (int i = tipo.size() - 1; i >= 0; i--) {
				Type g = tipo.get(i);
				if (g.toString().equals("int")) {
					c.write("%d");
				}
				if (g.toString().equals("double")) {
					c.write("%lf");
				}
			}
			c.write("\",");
			for (int i = id.size() - 1; i >= 0; i--) {
				Identifier g = id.get(i);
				c.write("&");
				g.scriviCodice(c);
				if (i > 0) {
					c.write(",");
				}
			}
			c.write(");\n");
			c.println();
		}

		@Override
		public void startScoping(Env e) {
			if (id.size() == tipo.size()) {
				for (int i = id.size() - 1; i >= 0; i--) {
					id.get(i).startScoping(e);
					tipo.get(i).startScoping(e);
					String type1 = id.get(i).getType();
					String type2 = tipo.get(i).getType();
					if (type1.equals(type2)) {
						type = "void";
					} else {
						throw new IllegalArgumentException("tipi incompatibili:" + type1 + " e " + type2);
					}
				}
			} else {
				throw new IllegalArgumentException("gli argomenti della read non sono uguali");
			}
		}

		/**
		 * manutenzione
		 */
		@Override
		public void drawNode(XMLStreamWriter x, TracciaDati t) throws Exception {
			x.writeStartElement("NODO" + t.incrementaNodi());
			x.writeAttribute("statement", "READ");
			for (int i = id.size() - 1; i >= 0; i--) {
				x.writeAttribute("varD", id.get(i).toString());
			}
		}

		@Override
		public void controlFlowDati(TracciaDati t) throws Exception {
			for (int i = id.size() - 1; i >= 0; i--) {
				t.aggiornaEspressione(id.get(i).toString(),"d");
				t.incrementaPassaggi();
			}
			t.aggiornaEspressioneNonUsate();
		}
	}

	public static Stat makeRead(List<Identifier> id, List<Type> tipo) {
		return new ReadOP(id, tipo);
	}

	// writeOp
	public static class WriteOP extends Stat {
		private List<Expr> expr;
		private String type;

		private WriteOP(List<Expr> expr) {
			this.expr = expr;
			this.type = null;
		}

		public String toString() {
			String frase = "";
			frase = expr.get(0) + frase;
			for (int i = 1; i < expr.size(); i++) {
				frase = expr.get(i) + "," + frase;
			}
			return frase + " -> " + "\n";
		}

		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			x.writeStartElement("WRITEOP");
			if (type != null) {
				x.writeAttribute("TYPE", type);
			}
			for (int i = expr.size() - 1; i >= 0; i--) {
				Expr g = expr.get(i);
				g.drawComponent(x);
			}
			x.writeEndElement();
		}

		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			c.write("	printf(");
			for (int i = expr.size() - 1; i >= 0; i--) {
				Expr g = expr.get(i);
				g.scriviCodice(c);
				if (i > 0) {
					c.write(",");
				}
			}
			c.write(");");
			c.println();
		}

		@Override
		public void startScoping(Env e) {
			for (int i = expr.size() - 1; i >= 0; i--) {
				expr.get(i).startScoping(e);
			}
			type = "void";
		}

		/**
		 * manutenzione
		 */
		@Override
		public void drawNode(XMLStreamWriter x, TracciaDati t) throws Exception {
			x.writeStartElement("NODO" + t.incrementaNodi());
			x.writeAttribute("statement", "WRITE");
			for (int i = expr.size() - 1; i >= 0; i--) {
				expr.get(i).drawNode(x, t);
			}
		}

		@Override
		public void controlFlowDati(TracciaDati t) throws Exception {
			for (int i = expr.size() - 1; i >= 0; i--) {
				Expr ex=expr.get(i);
				ex.controlFlowDati(t);
			}
			t.incrementaPassaggi();
			t.aggiornaEspressioneNonUsate();
		}
	}

	public static Stat writeOP1(List<Expr> val) {
		return new WriteOP(val);
	}

	// nodo assignOP
	public static class AssignOP extends Stat {
		private String id;
		private Expr expr;
		private String typeID;
		private String typeNode;
		private Bool_expr bool_expr;

		public AssignOP(String id, Expr expr) {
			this.id = id;
			this.expr = expr;
			this.typeID = null;
			this.typeNode = null;
			this.bool_expr = null;
		}

		public AssignOP(String id, Bool_expr expr) {
			this.id = id;
			this.bool_expr = expr;
			this.typeID = null;
			this.typeNode = null;
			this.expr = null;
		}

		public String toString() {
			if (expr == null) {
				return id + "=" + bool_expr + "\n";
			} else {
				return id + "=" + expr + "\n";
			}
		}

		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			x.writeStartElement("ASSIGNOP");
			if (typeNode != null) {
				x.writeAttribute("TYPE", typeNode);
			}
			x.writeStartElement("IDENTIFIER");
			if (typeID != null) {
				x.writeAttribute("TYPE", typeID);
			}
			x.writeAttribute("NAME", id);
			x.writeEndElement();
			if (expr == null) {
				bool_expr.drawComponent(x);
			} else {
				expr.drawComponent(x);
			}
			x.writeEndElement();
		}
		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			c.write("	" + id + "=");
			if (expr != null) {
				expr.scriviCodice(c);
			} else {
				bool_expr.scriviCodice(c);
			}
			c.write(";");
			c.println();
		}

		@Override
		public void startScoping(Env e) {
			typeID = e.getElementTables(id);
			if (typeID == null) {
				throw new IllegalArgumentException("variabile " + id + " non dichiarata");
			}
			if (expr != null) {
				expr.startScoping(e);
				String t = expr.getType();
				if (typeID.equals(t)) {
					typeNode = "void";
				} else {
					throw new IllegalArgumentException("Type Mismatch in Assign");
				}
			} else {
				bool_expr.startScoping(e);
				String t2 = bool_expr.getType();
				if (typeID.equals(t2)) {
					typeNode = "void";
				} else {
					throw new IllegalArgumentException("Type Mismatch in AssignOp sull'espressione booleana");
				}
			}
		}
		/**
		 * manutenzione
		 */
		@Override
		public void drawNode(XMLStreamWriter x, TracciaDati t) throws Exception {
			x.writeStartElement("NODO"+t.incrementaNodi());
			x.writeAttribute("statement", "ASSIGN");
			x.writeAttribute("varD",id);
			if (expr != null) {
				expr.drawNode(x, t);
			} 
			else {
				bool_expr.drawNode(x, t);
			}
		}

		@Override
		public void controlFlowDati(TracciaDati t) throws Exception {
			if(expr!=null) {
				expr.controlFlowDati(t);
				if(!(expr.getClass().getName().equals("yapl2.Expr$IntDoubleConst"))){
					t.incrementaPassaggi();
					t.aggiornaEspressioneNonUsate();
				}
			}
			else {
				bool_expr.controlFlowDati(t);
				if(!(bool_expr.getClass().getName().equals("yapl2.Bool_expr$BoolConst"))){
					t.incrementaPassaggi();
					t.aggiornaEspressioneNonUsate();
				}
			}
			t.aggiornaEspressione(id,"d");
			t.incrementaPassaggi();
			t.aggiornaEspressioneNonUsate();
		}
	}

	public static Stat assign(String id, Expr expr) {
		return new AssignOP(id, expr);
	}

	public static Stat assignBool(String id, Bool_expr expr) {
		return new AssignOP(id, expr);
	}

	// nodo callOp
	public static class CallOp extends Stat {
		private String id;
		private List<Expr> expr;
		private List<Identifier> listId;
		private String type;

		public CallOp(String id, List<Expr> expr, List<Identifier> listId) {
			this.expr = expr;
			this.id = id;
			this.listId = listId;
			this.type = null;
		}

		public String toString() {
			String frase = "";
			String frase2 = "";
			frase = expr.get(0) + frase;
			frase2 = listId.get(0) + frase2;
			for (int i = 1; i < expr.size(); i++) {
				frase = expr.get(i) + "," + frase;
			}
			for (int i = 1; i < listId.size(); i++) {
				frase2 = listId.get(i) + "," + frase2;
			}
			return id + "(" + frase + " : " + frase2 + ")\n";
		}

		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			x.writeStartElement("CALLOP");
			if (type != null) {
				x.writeAttribute("TYPE", type);
			}
			x.writeStartElement("IDENTIFIER");
			x.writeAttribute("NAME", id);
			x.writeEndElement();
			for (int i = expr.size() - 1; i >= 0; i--) {
				Expr g = expr.get(i);
				g.drawComponent(x);
			}
			for (int i = listId.size() - 1; i >= 0; i--) {
				Identifier g = listId.get(i);
				g.drawComponent(x);
			}
			x.writeEndElement();
		}

		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			c.write("      ");
			for (int i = listId.size() - 1; i >= 0; i--) {
				listId.get(i).scriviCodice(c);
			}
			c.write("=" + id + "(");
			for (int i = expr.size() - 1; i >= 0; i--) {
				expr.get(i).scriviCodice(c);
				if (i > 0) {
					c.write(",");
				}
			}
			c.write(");");
			c.println();
		}

		@Override
		public void startScoping(Env e) {
			String in = "";
			String out = "";
			int conta;
			if (e.existFunction(id)) {
				throw new IllegalArgumentException("Il nome della funzione non esiste " + id);
			} else {
				conta = 0;
				for (int i = expr.size() - 1; i >= 0; i--) {
					conta++;
					expr.get(i).startScoping(e);
					String t = expr.get(i).getType();
					in = e.getElementTables(id + conta);
					if (!(t.equals(in))) {
						throw new IllegalArgumentException("Tipi incompatibili nella chiamata " + id);
					}
				}
				for (int i = listId.size() - 1; i >= 0; i--) {
					conta++;
					listId.get(i).startScoping(e);
					String t = listId.get(i).getType();
					out = e.getElementTables(id + conta);
					type = out;
					if (!(t.equals(out))) {
						throw new IllegalArgumentException("Tipi incompatibili nella funzione " + id);
					}
				}
				if (!(e.getNumArgomenti(id + conta))) {
					throw new IllegalArgumentException(
							"Gli elementi inseriti sono minori rispetto alla definizione della funzione " + id);
				}
			}
		}
		/**
		 * manutenzione
		 */
		@Override
		public void drawNode(XMLStreamWriter x, TracciaDati t) throws Exception {
			x.writeStartElement("NODO"+t.incrementaNodi());
			x.writeAttribute("statement","CALL TO FUNCTION");
			x.writeAttribute("nomeFunzione",id);
			for (int i = expr.size() - 1; i >= 0; i--) {
				expr.get(i).drawNode(x, t);
			}
			for (int i = listId.size() - 1; i >= 0; i--) {
				listId.get(i).drawNode(x, t);
			}
		}

		@Override
		public void controlFlowDati(TracciaDati t) throws Exception {
			for (int i = expr.size() - 1; i >= 0; i--) {
				Expr exp=expr.get(i);
				exp.controlFlowDati(t);
			}
			for (int i = listId.size() - 1; i >= 0; i--) {
				Identifier id=listId.get(i);
				t.aggiornaEspressione(id.toString(), "d");
			}
			t.incrementaPassaggi();
			t.aggiornaEspressioneNonUsate();
		}
	}

	public static Stat call(String id, List<Expr> expr, List<Identifier> listId) {
		return new CallOp(id, expr, listId);
	}

	// nodo compStat
	public static class CompStatOp extends Stat {
		private List<Stat> stats;
		private String type;

		private CompStatOp(List<Stat> stats) {
			this.stats = stats;
			this.type = null;
		}

		public String toString() {
			String frase = "";
			for (Stat l : stats) {
				frase = l + frase;
			}
			return frase + "\n";
		}

		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			x.writeStartElement("COMPSTATOP");
			if (type != null) {
				x.writeAttribute("TYPE", type);
			}
			for (int i = stats.size() - 1; i >= 0; i--) {
				Stat g = stats.get(i);
				g.drawComponent(x);
			}
			x.writeEndElement();
		}

		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			c.write("	{\n");
			for (int i = stats.size() - 1; i >= 0; i--) {
				Stat g = stats.get(i);
				g.scriviCodice(c);
			}
			c.write("	}\n");
			c.println();
		}

		@Override
		public void startScoping(Env e) {
			for (int i = stats.size() - 1; i >= 0; i--) {
				stats.get(i).startScoping(e);
			}
			type = "void";
		}
		/**
		 * manutenzione
		 */
		@Override
		public void drawNode(XMLStreamWriter x, TracciaDati t) throws Exception {
			for(int i=stats.size()-1;i>=0;i--) {
				Stat st=stats.get(i);
				st.drawNode(x, t);
			}
			for(int i=stats.size()-1;i>=0;i--) {
				x.writeEndElement();
			}
		}
		@Override
		public void controlFlowDati(TracciaDati t) throws Exception {
			for (int i = stats.size() - 1; i >= 0; i--) {
				Stat stat1=stats.get(i);
				stat1.controlFlowDati(t);
			}
		}
	}
	public static Stat compStat(List<Stat> stats) {
		return new CompStatOp(stats);
	}
	// while
	public static class WhileOP extends Stat {
		private Bool_expr cond;
		private Stat stat;
		private String type;

		public WhileOP(Bool_expr cond, Stat stat) {
			this.cond = cond;
			this.stat = stat;
			this.type = null;
		}

		public String toString() {
			return "while (" + cond + ") \n do \n" + stat + "\n";
		}

		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			x.writeStartElement("WHILEOP");
			if (type != null) {
				x.writeAttribute("TYPE", type);
			}
			cond.drawComponent(x);
			stat.drawComponent(x);
			x.writeEndElement();
		}

		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			c.write("	while(");
			cond.scriviCodice(c);
			c.write(")");
			stat.scriviCodice(c);
			c.println();
		}

		@Override
		public void startScoping(Env e) {
			cond.startScoping(e);
			String t = cond.getType();
			if (t.equals("bool")) {
				type = "void";
			} else {
				throw new IllegalArgumentException("Type mismatch While");
			}
			stat.startScoping(e);
		}
		/**
		 * manutenzione
		 */
		@Override
		public void drawNode(XMLStreamWriter x, TracciaDati t) throws Exception {
			x.writeStartElement("NODO"+t.incrementaNodi());
			x.writeAttribute("statement", "WHILE");
			cond.drawNode(x, t);
			stat.drawNode(x, t);
		}

		@Override
		public void controlFlowDati(TracciaDati t) throws Exception {
			cond.controlFlowDati(t);
			t.incrementaPassaggi();
			t.aggiornaEspressioneNonUsate();
			stat.controlFlowDati(t);
		}
	}

	public static Stat loopWhile(Bool_expr cond, Stat stat) {
		return new WhileOP(cond, stat);
	}

	// ifthenop
	public static class IfThenOp extends Stat {
		private Bool_expr expr;
		private Stat stat;
		private String type;

		public IfThenOp(Bool_expr expr, Stat stat) {
			this.expr = expr;
			this.stat = stat;
			this.type = null;
		}

		public String toString() {
			return "if(" + expr + ") then\n" + stat + "\n";
		}

		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			x.writeStartElement("IFTHENOP");
			if (type != null) {
				x.writeAttribute("TYPE", type);
			}
			expr.drawComponent(x);
			stat.drawComponent(x);
			x.writeEndElement();
		}

		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			c.write("	if(");
			expr.scriviCodice(c);
			c.write(")");
			stat.scriviCodice(c);
			c.println();
		}

		@Override
		public void startScoping(Env e) {
			expr.startScoping(e);
			String t = expr.getType();
			if (t.equals("bool")) {
				type = "void";
			} else {
				throw new IllegalArgumentException("Type mismatch in ifThenOp");
			}
			stat.startScoping(e);
		}
		
		/**
		 * manutenzione
		 */
		@Override
		public void drawNode(XMLStreamWriter x, TracciaDati t) throws Exception {
			x.writeStartElement("NODO"+t.incrementaNodi());
			x.writeAttribute("statement", "IF");
			expr.drawNode(x, t);
			stat.drawNode(x,t);
		}

		@Override
		public void controlFlowDati(TracciaDati t) throws Exception {
			expr.controlFlowDati(t);
			t.incrementaPassaggi();
			t.aggiornaEspressioneNonUsate();
			stat.controlFlowDati(t);
		}
	}

	public static Stat ifThen(Bool_expr expr, Stat stat) {
		return new IfThenOp(expr, stat);
	}

	// ifelse
	public static class IfThenElseOP extends Stat {
		private Bool_expr expr;
		private Stat stat;
		private Stat stat2;
		private String type;

		public IfThenElseOP(Bool_expr expr, Stat stat, Stat stat2) {
			this.expr = expr;
			this.stat = stat;
			this.stat2 = stat2;
			this.type = null;
		}

		public String toString() {
			return "if(" + expr + ") then \n " + stat + "else\n" + stat2 + "\n";
		}

		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			x.writeStartElement("IFTHENELSEOP");
			if (type != null) {
				x.writeAttribute("TYPE", type);
			}
			expr.drawComponent(x);
			stat.drawComponent(x);
			stat2.drawComponent(x);
			x.writeEndElement();
		}

		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			c.write("	if(");
			expr.scriviCodice(c);
			c.write(")");
			stat.scriviCodice(c);
			c.write("	else");
			stat2.scriviCodice(c);
			c.println();
		}

		@Override
		public void startScoping(Env e) {
			expr.startScoping(e);
			String t = expr.getType();
			if (t.equals("bool")) {
				type = "void";
			} else {
				throw new IllegalArgumentException("Type Mismatch IfthenElse");
			}
			stat.startScoping(e);
			stat2.startScoping(e);
		}
		/**
		 * manutenzione
		 */
		@Override
		public void drawNode(XMLStreamWriter x, TracciaDati t) throws Exception {
			x.writeStartElement("NODO"+t.incrementaNodi());
			x.writeAttribute("statement", "IFELSE");
			expr.drawNode(x, t);
			stat.drawNode(x, t);
			stat2.drawNode(x, t);
		}
		@Override
		public void controlFlowDati(TracciaDati t) throws Exception {
			expr.controlFlowDati(t);
			t.incrementaPassaggi();
			t.aggiornaEspressioneNonUsate();
			stat.controlFlowDati(t);
			stat2.controlFlowDati(t);
		}
	}

	public static Stat ifThenElse(Bool_expr expr, Stat stat, Stat stat2) {
		return new IfThenElseOP(expr, stat, stat2);
	}
	
}
package yapl2;

import java.io.PrintWriter;
import javax.xml.stream.XMLStreamWriter;
import cup.example.sym;
import drawTree.TreeComponent;
import esercitazione5_COMP.*;

//collaudata
public abstract class Bool_expr implements sym,TreeComponent,ScriviCodice,AzioniSemanticheNodi,OttieniTipo{
	
	//Priorità
	public static class Priority2 extends Bool_expr{
		private Bool_expr e;
		private String type;
		private Priority2(Bool_expr e){
			this.e=e;
			this.type=null;
		}
		public String toString(){
			return "("+e+")";
		}
		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			x.writeStartElement("PRIORITYBOOLEAN");
			if(type!=null){
				x.writeAttribute("TYPE", type);
			}
			e.drawComponent(x);
			x.writeEndElement();
		}
		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			c.write("(");
			e.scriviCodice(c);
			c.write(")");
		}
		@Override
		public void startScoping(Env e) {
			this.e.startScoping(e);
			type=this.e.getType();
		}
		@Override
		public String getType() {
			return type;
		}
	}
	public static Priority2 priority(Bool_expr e){
		return new Priority2(e);
	}

	//nodo opBinExpr
	public static class BoolOP extends Bool_expr {
		private Bool_expr e1, e2;
		private int op;
		private String type;
		private Expr expr1,expr2;
		public BoolOP(Bool_expr e1, int op, Bool_expr e2){
			this.e1=e1;
			this.e2=e2;
			this.op=op;
			this.type=null;
			this.expr1=null;
			this.expr2=null;
		}
		public BoolOP(Expr e1,int op,Expr e2){
			this.expr1=e1;
			this.expr2=e2;
			this.op=op;
			this.e1=null;
			this.e2=null;
			this.type=null;
		}
		public String toString(){
			String operator="";
			if (op==sym.AND)  operator = "&&";
			if (op==sym.OR) operator = "||";
			if((e1!=null)&&(e2!=null)){
				return e1 + operator + e2;
			}
			else{
				return expr1 +operator +expr2;
			}
			
		}
		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			String operator=null;
			if(op==sym.AND) operator="ANDOP";
			if (op==sym.OR) operator = "OROP";
			x.writeStartElement(operator);
			if(type!=null){
				x.writeAttribute("TYPE", type);
			}
			if((e1!=null)&&(e2!=null)){
				e1.drawComponent(x);
				e2.drawComponent(x);
			}
			else{
				expr1.drawComponent(x);
				expr2.drawComponent(x);
			}
			x.writeEndElement();
		}
		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			String operator=null;
			if(op==sym.AND) operator="&&";
			if (op==sym.OR) operator = "||";
			if((e1!=null)&&(e2!=null)){
				e1.scriviCodice(c);
				c.write(operator);
				e2.scriviCodice(c);	
			}
			else{
				expr1.scriviCodice(c);
				c.write(operator);
				expr2.scriviCodice(c);	
			}
			
		}
		@Override
		public void startScoping(Env e) {
			if((e1!=null)&&(e2!=null)){
				e1.startScoping(e);
				String t1=e1.getType();
				e2.startScoping(e);
				String t2=e2.getType();
				if(t1.equals(t2)){
					type="bool";
				}
				else{
					throw new IllegalArgumentException("Type Mismatch BoolOp");
				}
			}
			else{
				expr1.startScoping(e);
				String t1=expr1.getType();
				expr2.startScoping(e);
				String t2=expr2.getType();
				if(t1.equals(t2)){
					type="bool";
				}
				else{
					throw new IllegalArgumentException("Type Mismatch BoolOp espressioni");
				}
			}
		}
		@Override
		public String getType() {
			return type;
		}
	}
	
	public static BoolOP binop(Bool_expr e1, int op, Bool_expr e2){
		return new BoolOP(e1,op,e2);
	}
	
	public static BoolOP binExpr(Expr e1,int op,Expr e2){
		return new BoolOP(e1,op,e2);
	}
	
	//nodo not
	public static class NotUnex extends Bool_expr {
		private Bool_expr e1;
		private String type;
		private NotUnex(Bool_expr e1){
			this.e1=e1;
			this.type=null;
		}
		public String toString(){
			return "not"+e1;
		}
		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			x.writeStartElement("NOTOP");
			if(type!=null){
				x.writeAttribute("TYPE", type);
			}
			e1.drawComponent(x);
			x.writeEndElement();
		}
		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			c.write("!");
			e1.scriviCodice(c);
		}
		@Override
		public void startScoping(Env e) {
			e1.startScoping(e);
			String t=e1.getType();
			if(t.equals("bool")){
				type="bool";
			}
			else{
				throw new IllegalArgumentException("Type mismatch in notOP");
			}
		}
		@Override
		public String getType() {
			return type;
		}
	}
	public static NotUnex unop(Bool_expr e){
		return new NotUnex(e);
	}
	
	//nodo costanti booleani
	public static class BoolConst extends Bool_expr {
		private boolean i;
		private String type;
		private BoolConst(boolean i){
			this.i=i;
			this.type=null;
		}
		public String toString(){
			String frase="";
			if(i){frase="true";}
			if(!i){frase="false";}
			return frase;
		}
		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			String frase="";
			if(i){frase="TRUE";}
			if(!i){frase="FALSE";}
			x.writeStartElement(frase);
			if(type!=null){
				x.writeAttribute("TYPE", type);
			}
			x.writeEndElement();
		}
		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			String frase="";
			if(i){frase="true";}
			if(!i){frase="false";}
			c.write(frase);
		}
		@Override
		public void startScoping(Env e) {
			type="bool";
		}
		@Override
		public String getType() {
			return type;
		}
	}
	
	public static BoolConst boolConst(boolean c){
		return new BoolConst(c);
	}
	
	//nodo idBool
	public static class IdentifierBool extends Bool_expr {
		private String i;
		private String type;
		private IdentifierBool(String i){
			this.i=i;	
			this.type=null;
		}
		public String toString(){
			return i;
		}
		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			x.writeStartElement("IDENTIFIER");
			if(type!=null){
				x.writeAttribute("TYPE", type);
			}
			x.writeAttribute("NAME", i);
			x.writeEndElement();
		}
		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			c.write(i);
		}
		@Override
		public void startScoping(Env e) {
			type=e.getElementTables(i);
			if(type==null){
				throw new IllegalArgumentException("variabile inesistente " +i);
			}
		}
		@Override
		public String getType() {
			return type;
		}
	}
	
	public static IdentifierBool ident(String s){
		return new IdentifierBool(s);
	}
	
	//nodo opRel_op
	public static class RelOP extends Bool_expr {
		private Expr e1, e2;
		private int op;
		private String type;
		public RelOP(Expr e1, int op, Expr e2){
			this.e1=e1;
			this.e2=e2;
			this.op=op;
			this.type=null;
		}
		public String toString(){
			String operator=null;
			if (op==sym.GT)  operator = ">";
			if (op==sym.GE) operator = ">=";
			if (op==sym.EQ) operator = "==";
			if (op==sym.LT) operator = "<";
			if (op==sym.LE) operator = "<=";
			return e1 + operator + e2;
		}
		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			String operator=null;
			if (op==sym.GT)  operator = "GTOP";
			if (op==sym.GE) operator = "GEOP";
			if (op==sym.EQ) operator = "EQOP";
			if (op==sym.LT) operator = "LTOP";
			if (op==sym.LE) operator = "LEOP";
			x.writeStartElement(operator);
			if(type!=null){
				x.writeAttribute("TYPE", type);
			}
			e1.drawComponent(x);
			e2.drawComponent(x);
			x.writeEndElement();
		}
		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			String operator=null;
			if (op==sym.GT)  operator = ">";
			if (op==sym.GE) operator = ">=";
			if (op==sym.EQ) operator = "==";
			if (op==sym.LT) operator = "<";
			if (op==sym.LE) operator = "<=";
			e1.scriviCodice(c);
			c.write(operator);
			e2.scriviCodice(c);
		}
		@Override
		public void startScoping(Env e) {
			e1.startScoping(e);
			String t=e1.getType();
			e2.startScoping(e);
			String t2=e2.getType();
			if(t.equals(t2)){
				type="bool";
			}
			else{
				throw new IllegalArgumentException("Type Mismatch in Relop");
			}
		}
		@Override
		public String getType() {
			return type;
		}
	}
	
	public static RelOP relop(Expr e1, int op, Expr e2){
		return new RelOP(e1,op,e2);
	}
	
}

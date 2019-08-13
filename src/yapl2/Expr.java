package yapl2;

import java.io.PrintWriter;
import javax.xml.stream.XMLStreamWriter;
import analizzatoreSemantico.Env;
import analizzatoreSemantico.OttieniTipo;
import cup.example.sym;
import toolManutenzione.*;

//collaudata
public abstract class Expr implements sym,AzioniCompilatore,OttieniTipo{
	//Priorità
	public static class Priority extends Expr {
		private Expr e;
		private String type;
		public Priority(Expr e){
			this.e=e;
			type=null;
		}
		public String toString(){
			return "("+e+")\n";
		}
		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			x.writeStartElement("PRIORITY");
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
		public String getType(){
			return type;
		}
		/**
		 * manutenzione
		 */
		@Override
		public void controlFlowDati(TracciaDati t) throws Exception {
			e.controlFlowDati(t);
		}
	}
	
	public static Priority priority(Expr e){
		return new Priority(e);
	}
	
	//nodo Binex
	public static class Binex extends Expr {
		private Expr e1, e2;
		private int op;
		private String type;
		public Binex(Expr e1, int op, Expr e2){
			this.e1=e1;
			this.e2=e2;
			this.op=op;
			type=null;
		}
		public String toString(){
			String operator=null;
			if (op==sym.PLUS)  operator = "+";
			if (op==sym.MINUS) operator = "-";
			if (op==sym.TIMES)  operator = "*";
			if (op==sym.DIV)   operator = "/";
			return e1 + ""+operator + e2 + "\n";
		}
		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			String operator=null;
			if (op==sym.PLUS)  operator = "ADDOP";
			if (op==sym.MINUS) operator = "DIFFOP";
			if (op==sym.TIMES)  operator = "MULOP";
			if (op==sym.DIV)   operator = "DIVOP";
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
			if (op==sym.PLUS)  operator = "+";
			if (op==sym.MINUS) operator = "-";
			if (op==sym.TIMES)  operator = "*";
			if (op==sym.DIV)   operator = "/";
			e1.scriviCodice(c);
			c.write(operator);
			e2.scriviCodice(c);
		}
		@Override
		public void startScoping(Env e) {
			e1.startScoping(e);
			e2.startScoping(e);
			String type1=e1.getType();
			String type2=e2.getType();
			if(type1.equals(type2)){
				type=type1;
			}
			else{
				throw new IllegalArgumentException("tipi incompatibili in binex:"+ type1 + " "+ type2);
			}
		}
		@Override
		public String getType() {
			return type;
		}
		/**
		 * manutenzione
		 */
		@Override
		public void controlFlowDati(TracciaDati t) throws Exception {
			e1.controlFlowDati(t);
			e2.controlFlowDati(t);			
		}
	}
	
	public static Binex makeExprArith(Expr e1, int op, Expr e2){
		return new Binex(e1,op,e2);
	}
	
	//nodo Unex
	public static class Unex extends Expr {
		private Expr e1;
		private String type;
		public Unex(Expr e1){
			this.e1=e1;
			this.type=null;
		}
		public String toString(){
			return "-"+e1 + "\n";
		}
		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			x.writeStartElement("UMINUSOP");
			if(type!=null){
				x.writeAttribute("TYPE", type);
			}
			e1.drawComponent(x);
			x.writeEndElement();
		}
		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			c.write("-");
			e1.scriviCodice(c);
		}
		@Override
		public void startScoping(Env e) {
			e1.startScoping(e);
			type=e1.getType();
			if(!((type.equals("double"))||(type.equals("int")))){
				throw new IllegalArgumentException("tipi incompatibili:"+ type);
			}
		}
		@Override
		public String getType() {
			return type;
		}
		/**
		 * manutenzione
		 */
		@Override
		public void controlFlowDati(TracciaDati t) throws Exception {
			e1.controlFlowDati(t);
		}
	}
	
	public static Unex unop(Expr e){
		return new Unex(e);
	}
	
	//nodo costanti
	public static class IntDoubleConst extends Expr {
		private Double doppio;
		private Integer intero;
		private String type;
		public IntDoubleConst(Double doppio){
			this.doppio=doppio;
			this.type=null;
		}
		public IntDoubleConst(Integer intero){
			this.intero=intero;
			this.type=null;
		}
		public String toString(){
			if(doppio==null){
				return intero+"";
			}
			else{
				return doppio+"";
			}
		}
		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			if(doppio==null){
				x.writeStartElement("INT_CONST");
				if(type!=null){
					x.writeAttribute("TYPE", type);
				}
				x.writeAttribute("VALUE",""+intero);
				x.writeEndElement();
			}
			else{
				x.writeStartElement("DOUBLE_CONST");
				if(type!=null){
					x.writeAttribute("TYPE", type);
				}
				x.writeAttribute("VALUE", ""+doppio);
				x.writeEndElement();
			}
		}
		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			if(doppio==null){
				c.write(intero+"");
			}
			else{
				c.write(doppio+"");
			}
		}
		@Override
		public void startScoping(Env e) {
			if(doppio==null){
				type="int";
			}
			else{
				type="double";
			}
		}
		@Override
		public String getType() {
			return type;
		}
		/**
		 * manutenzione
		 */
		@Override
		public void controlFlowDati(TracciaDati t) throws Exception {
			/*do nothing*/
			
		}
	}
	
	public static IntDoubleConst intconst(Integer i){
		return new IntDoubleConst(i);
	}
	public static IntDoubleConst doubleconst(Double c){
		return new IntDoubleConst(c);
	}
	
	//nodo identifier
	public static class Identifier extends Expr {
		private String i;
		private String type;
		public Identifier(String i){
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
				throw new IllegalArgumentException("variabile "+ i + " non visibile in questo scope o non dichiarata");
			}
		}
		@Override
		public String getType() {
			return type;
		}
		/**
		 * manutenzione
		 */
		@Override
		public void controlFlowDati(TracciaDati t) throws Exception {
			t.aggiornaEspressione(i,"u");
		}	
	}
	
	public static Identifier ident(String s){
		return new Identifier(s);
	}
	
	//nodo String_const
		public static class StringConst extends Expr {
			private String i;
			private String type;
			public StringConst(String i){
			    this.i=i;
			}
			public String toString(){
			    return i;
			}
			@Override
			public void drawComponent(XMLStreamWriter x) throws Exception {
				x.writeStartElement("STRING_CONST");
				x.writeAttribute("VALUE", i);
				x.writeEndElement();
			}
			@Override
			public void scriviCodice(PrintWriter c) throws Exception {
				c.write("\""+i+"\\n\"");
			}
			@Override
			public void startScoping(Env e) {
				type="void";
			}
			@Override
			public String getType() {
				return type;
			}
			/**
			 * manutenzione
			 */
			@Override
			public void controlFlowDati(TracciaDati t) throws Exception {
				/*do nothing*/
			}
		}
		
	public static StringConst makeStringConst(String s){
		return new StringConst(s);
	}
		
		//nodo TYPE
		public static class Type extends Expr {
			private String i;
			private String type;
			public Type(String i){
			    this.i=i;
			}
			public String toString(){
			    return i;
			}
			@Override
			public void drawComponent(XMLStreamWriter x) throws Exception {
				String type=null;
				if(i.equals("int")){type="INTEGER";}
				if(i.equals("double")){type="DOUBLE";}
				if(i.equals("bool")){type="BOOLEAN";}
				x.writeStartElement(type);
				x.writeEndElement();
			}
			@Override
			public void scriviCodice(PrintWriter c) throws Exception {
				if(i.equals("bool")){
					i="int";
				}
				c.write(i+" ");
			}
			@Override
			public void startScoping(Env e) {
				type=i;
			}
			@Override
			public String getType() {
				return type;
			}
			/**
			 * manutenzione
			 */
			@Override
			public void controlFlowDati(TracciaDati t) throws Exception {
				/*do nothing*/
			}
		}
		
		public static Type makeType(String s){
			return new Type(s);
		}
}

package yapl2;

import java.util.*;
import java.io.PrintWriter;

import javax.xml.stream.XMLStreamWriter;

import yapl2.Var_decl.*;
import yapl2.Expr.*;
import drawTree.TreeComponent;
import esercitazione5_COMP.*;

//collaudata
public abstract class Par_decl implements TreeComponent,ScriviCodice,AzioniSemanticheNodi,OttieniTipo{
	//nodo ParDecl
	public static class ParDeclOP extends Par_decl{
		private Type type;
		private VarOp varOp;
		public ParDeclOP(Type type,VarOp varOp){
			this.type=type;
			this.varOp=varOp;
		}
		public String toString(){
			return  type + " " + varOp+ "\n";
		}
		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			x.writeStartElement("PARDECLOP");
			type.drawComponent(x);
			varOp.drawComponent(x);
			x.writeEndElement();
		}
		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			varOp.scriviCodice(c);
		}
		@Override
		public void startScoping(Env e) {
			if(e.getFunction()!=null){//qui vengono inseriti gli identificatori della funzione nella tabella globale di scope
				List<Identifier> list=varOp.getID();
				for(int i=list.size()-1;i>=0;i--){
					e.addVariabile(type.toString(),e.getFunction());
				}
			}
			else{
				varOp.startScoping(e);//qui vengono inserite le variabili di output nello scope della funzione.
				List<Identifier> list=varOp.getID();
				if(list.size()>1){
					throw new IllegalArgumentException("hai inserito pi� di un identifcatore all'interno della dichiarazione di output della funzione");
				}
				for(int i=list.size()-1;i>=0;i--){
					e.addVariabile(type.toString(), list.get(i).toString());
				}
			}
		}
		@Override
		public String getType(){
			return type.toString();
		}
	}
	
	public static ParDeclOP pardeclOP(Type type,VarOp varOp){
		return new ParDeclOP(type,varOp);
	}
}

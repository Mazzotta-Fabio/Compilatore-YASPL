package astcomponent;

import java.util.*;
import java.io.PrintWriter;
import javax.xml.stream.XMLStreamWriter;
import astcomponent.Expr.*;
import astcomponent.Var_decl.*;
import graphcomponent.*;
import scopehandler.*;

//collaudata
public abstract class Par_decl implements AzioniCompilatore,OttieniTipo{
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
					throw new IllegalArgumentException("hai inserito più di un identificatore all'interno della dichiarazione di output della funzione");
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
		/**
		 * manutenzione
		 */
		@Override
		public void buildControlFlow(Graph<String> g) {
			List<Identifier> list=varOp.getID();
			for(int i=list.size()-1;i>=0;i--) {
				Vertex<String> lastNode=g.getLastNode();
				g.insertVertex("RETURNVALUEFUNCTION");
				g.getLastNode().addVarUs(list.get(i).toString());
				g.insertDirectedEdge(lastNode,g.getLastNode(),"NORMAL");
			}
		}
	}
	
	public static ParDeclOP pardeclOP(Type type,VarOp varOp){
		return new ParDeclOP(type,varOp);
	}
}

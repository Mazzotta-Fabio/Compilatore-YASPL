package yapl2;

import java.io.PrintWriter;
import java.util.*;
import javax.xml.stream.XMLStreamWriter;
import yapl2.Expr.*;
import drawTree.TreeComponent;
import esercitazione5_COMP.*;

//nodo var_decl
public abstract class Var_decl implements TreeComponent,ScriviCodice,AzioniSemanticheNodi{

	public static class VarDeclOP extends Var_decl {
		private Type type;
		private VarOp var;
		public VarDeclOP(Type type,VarOp var){
			this.var=var;
			this.type=type;
		}	
		public String toString(){
			return  type + " " +  var + "\n";
		}
		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			x.writeStartElement("VARDECLOP");
			type.drawComponent(x);
			var.drawComponent(x);
			x.writeEndElement();
		}
		public Type getType(){
			return type;
		}
		public VarOp getVarOp(){
			return var;
		}
		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			type.scriviCodice(c);
			var.scriviCodice(c);
		}
		@Override
		public void startScoping(Env e) {
			//qui vengono inseriti gli identificatori della funzione nella tabella globale di scope
			if(e.getFunction()!=null){
				List<Identifier> list=var.getID();
				if(list.size()>1){
					throw new IllegalArgumentException("hai inserito più di un identificatore nella dichiarazione nella definizione della funzione "+e.getFunction());
				}
				for(int i=list.size()-1;i>=0;i--){
					e.addVariabile(type.toString(),e.getFunction());
				}
			}
			else{
				var.startScoping(e);
				List<Identifier> list=var.getID();
				for(int i=list.size()-1;i>=0;i--){
					if((e.getElementTables(list.get(i).toString())==null)||(e.getEnv()!=null)){//controllo per inserire le variabili con lo stesso nome nello scope della funzione
						e.addVariabile(type.toString(), list.get(i).toString());
					}
					else{
						throw new IllegalArgumentException("la variabile "+list.get(i).toString()+" è già esistente in questa dichiarazione");
					}
				}
			}
		}
	}
	
	public static VarDeclOP getVarDeclOP(Type type,VarOp ops){
		return new VarDeclOP(type,ops);
	}
	
	public static class VarOp extends Var_decl{
		private List<Identifier> id;
		public VarOp(){
			this.id=new ArrayList<Identifier>();
		}
		public String toString(){
			String frase=""+id.get(0);
			for (int i = 1; i < id.size(); i++){
				frase=id.get(i)+","+frase;
			}
			return frase;
		}
		public void addIdentifier(Identifier v){
			id.add(v);
		}
		public List<Identifier> getID(){
			return id; 
		}
		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			for(int i=id.size()-1;i>=0;i--){
				x.writeStartElement("VAROP");
				Identifier c=id.get(i);
				c.drawComponent(x);
			}
			for(int i=id.size()-1;i>=0;i--){
				x.writeEndElement();
			}
		}
		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			String frase=""+id.get(0);
			for (int i = 1; i < id.size(); i++){
				frase=id.get(i)+","+frase;
			}
			c.write(frase);
		}
		
		@Override
		public void startScoping(Env e) {
			for(int i=id.size()-1;i>=0;i--){
				if(e.getElementLocal(id.get(i).toString())){
					throw new IllegalArgumentException("dichiarazione multipla variabile "+ id.get(i).toString());
				}
			}
		}
	}
	public static VarOp makeVarOP(){
		return new VarOp();
	}
	
}

package astcomponent;

//collaudata
import java.io.*;
import java.util.List;
import javax.xml.stream.XMLStreamWriter;
import astcomponent.Expr.*;
import graphcomponent.*;
import scopehandler.Env;

//nodo def_decl
public abstract class Def_decl implements AzioniCompilatore{
	public static class ProcDeclOp extends Def_decl {
		private Identifier attribute;
		private List<Var_decl> listVar;
		private List<Par_decl> parDecl;
		private Body body;	
		private String type;
		public ProcDeclOp(Identifier attribute,List<Var_decl> listVar,List<Par_decl>parDecl,Body body){
			this.listVar=listVar;
			this.parDecl=parDecl;
			this.attribute=attribute;
			this.body=body;
			this.type=null;
		}
		public String toString(){
			String frase2="";
			String frase="";
			for(Var_decl c:listVar){
				frase2=c+frase2;
			}
			for(Par_decl b:parDecl){
				frase=b+frase;
			}
			return "def " + attribute + "(" + frase2 +"):" + frase + "{\n" + body + "} \n";
		}
		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception {
			x.writeStartElement("PROCDECLOP");
			if(type!=null){
				x.writeAttribute("TYPE", type);
			}
			attribute.drawComponent(x);
			for(int i=listVar.size()-1;i>=0;i--){
				Var_decl g=listVar.get(i);
				g.drawComponent(x);
			}
			for(int i=parDecl.size()-1;i>=0;i--){
				Par_decl g=parDecl.get(i);
				g.drawComponent(x);
			}
			body.drawComponent(x);
			x.writeEndElement();
		}
		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			c.write(type+" "+attribute.toString()+"(");
			for(int i=listVar.size()-1;i>=0;i--){
				listVar.get(i).scriviCodice(c);
				if(i>0){
					c.write(",");
				}
			}
			c.write("){\n      "+type + " ");
			for(int i=parDecl.size()-1;i>=0;i--){
				parDecl.get(i).scriviCodice(c);
			}
			c.write(";\n");
			body.scriviCodice(c);
			c.write("    return ");
			for(int i=parDecl.size()-1;i>=0;i--){
				parDecl.get(i).scriviCodice(c);
			}
			c.write(";");
			c.write("\n}\n");
			c.println();
		}
		@Override
		public void startScoping(Env e) {
			if(e.controllaNomeFunzione(attribute.toString())) {
				throw new IllegalArgumentException("Esiste una variabile dichiarata con questo nome: "+attribute.toString());
			}
			if(e.existFunction(attribute.toString())){
				e.addVariabile(attribute.toString(),"def"+attribute.toString());
				e.setFunction(attribute.toString());
				Env newEnv=new Env(e);
				for(int i=listVar.size()-1;i>=0;i--){
					listVar.get(i).startScoping(e);
					listVar.get(i).startScoping(newEnv);
				}
				if(parDecl.size()==1){
					for(int i=parDecl.size()-1;i>=0;i--){
						type=parDecl.get(i).getType();
						parDecl.get(i).startScoping(e);
						parDecl.get(i).startScoping(newEnv);
					}
				}
				else{
					throw new IllegalArgumentException("Hai inserito più di un argomento di output nella definizione della funzione");
				}
				body.startScoping(newEnv);
				e.setFunction(null);
			}
			else{
				throw new IllegalArgumentException("Esite già una funzione con questo nome " + attribute);
			}
		}
		/**
		 * manutenzione
		 */
		
		@Override
		public void buildControlFlow(Graph<String> g) {
			Vertex<String> u=g.insertVertex("DEFFUNZIONE "+attribute.toString());
			Vertex<String> v=g.insertVertex("ARGOMENTIFUNZIONE");
			g.insertDirectedEdge(u, v,"NORMAL");
			for(int i=listVar.size()-1;i>=0;i--){
				Var_decl vard=listVar.get(i);
				vard.buildControlFlow(g);
			}
			body.buildControlFlow(g);
			for(int i=parDecl.size()-1;i>=0;i--){
				Par_decl par=parDecl.get(i);
				par.buildControlFlow(g);
			}
		}
	}
	
	public static ProcDeclOp makeDef_decl(Identifier attribute,List<Var_decl> listVar,List<Par_decl> parDecl,Body body){
		return new ProcDeclOp(attribute,listVar,parDecl,body);
	}
	
}

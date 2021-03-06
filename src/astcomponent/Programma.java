package astcomponent;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamWriter;
import graphcomponent.Graph;
import scopehandler.Env;
import yasplcompiler.GraphGenerator;

//collaudata
public abstract class Programma implements AzioniCompilatore{
	//nodo ProgramOP
	public static class ProgramOp extends Programma{
		private Decls vars;
		private List<Stat> stat;
		public ProgramOp(Decls vars,List<Stat> stat) {
			this.stat= stat;
			this.vars=vars;
		}
		public String toString(){
			String ret2="";
			for(Stat s:stat){
				ret2=s+ret2;
			}
			return "head \n" + vars + "start \n" + ret2 + "\n" ;
		}
		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception{
			x.writeStartElement("PROGRAMOP");
			vars.drawComponent(x);
			for(int i=stat.size()-1;i>=0;i--){
				Stat g=stat.get(i);
				g.drawComponent(x);
			}
			x.writeEndElement();
		}
		@Override
		public void scriviCodice(PrintWriter c)throws Exception{
			c.println("#include<stdio.h> \n#include<stdlib.h>\n");
			vars.scriviCodice(c);
			c.println("int main(void){");
			for(int i=stat.size()-1;i>=0;i--){
				Stat g=stat.get(i);
				g.scriviCodice(c);
			}
			c.println("	  system(\"pause\");\n	return 0;\n}\n");
		}
		@Override
		public void startScoping(Env e) {
			vars.startScoping(e);
			for(int i=stat.size()-1;i>=0;i--){
				stat.get(i).startScoping(e);
			}
		}
		/**
		 * manutenzione
		 */
		
		@Override
		public void buildControlFlow(Graph<String> g) {
			vars.buildControlFlow(g);
			for(int i=stat.size()-1;i>=0;i--) {
				Stat st=stat.get(i);
				st.buildControlFlow(g);
			}
		}
	}
	
	public static ProgramOp programmaOP(Decls decl,List<Stat> stats){
		return new ProgramOp(decl,stats);
	}
	
	public static class Decls extends Programma{
		private ArrayList<Def_decl> decls;
		private ArrayList<Var_decl> vars;
		public Decls(){
			decls=new ArrayList<Def_decl>();
			vars=new ArrayList<Var_decl>();
		}
		public void addDecl(Def_decl decl){
			decls.add(decl);
		}
		public void addVars(Var_decl var){
			vars.add(var);
		}
		public String toString(){
			String ret="";
			for(Var_decl s:vars){
				ret=s+ret;
			}
			for(Def_decl d:decls){
				ret=d+ret;
			}
			return ret + "\n" ;
		}
		@Override
		public void drawComponent(XMLStreamWriter x)throws Exception{
			for(int i=vars.size()-1;i>=0;i--){
				Var_decl g=vars.get(i);
				g.drawComponent(x);
			}
			for(int i=decls.size()-1;i>=0;i--){
				Def_decl g=decls.get(i);
				g.drawComponent(x);
			}
		}
		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			for(int i=vars.size()-1;i>=0;i--){
				Var_decl g=vars.get(i);
				g.scriviCodice(c);
				c.write(";");
				c.println();
			}
			for(int i=decls.size()-1;i>=0;i--){
				Def_decl g=decls.get(i);
				g.scriviCodice(c);
			}
		}
		@Override
		public void startScoping(Env e) {
			for(int i=vars.size()-1;i>=0;i--){
				vars.get(i).startScoping(e);
			}
			for(int i=decls.size()-1;i>=0;i--){
				decls.get(i).startScoping(e);
			}
		}
		
		/**
		 * manutenzione
		 */
		@Override
		public void buildControlFlow(Graph<String> g) {
			g.insertVertex("DICHIARAZIONIVARIABILI");
			for(int i=vars.size()-1;i>=0;i--) {
				Var_decl var_decl=vars.get(i);
				var_decl.buildControlFlow(g);
			}
			for(int i=decls.size()-1;i>=0;i--) {
				Graph<String> gr=GraphGenerator.newGraph();
				Def_decl func=decls.get(i);
				func.buildControlFlow(gr);
				GraphGenerator.addGraph(gr);
			}
		}
	}
	
	public static Decls getDecls(){
		return new Decls();
	}
}

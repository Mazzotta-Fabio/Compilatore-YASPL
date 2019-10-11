package yasplcompiler;

import java.io.*;
import java.util.*;
import astcomponent.Programma;
import graphcomponent.*;

public class GraphGenerator {
	
	private Graph<String> g;
	private static ArrayList<Graph<String>> grafiFunzione;
	private ArrayList<Tripla> triple;
	private Token token;
	
	public GraphGenerator() {
		g=new Graph<String>();
		grafiFunzione=new ArrayList<Graph<String>>();
		triple=new ArrayList<Tripla>();
		token=null;
	}
	
	public void getControlFlowGraph(Programma p){
		p.buildControlFlow(g);
	}
	
	/*
	public void visualizzaCFG(PrintWriter x) {
		for(Graph<String> gr:grafiFunzione) {
			disegnaCFG(x,gr);
		}
		disegnaCFG(x,g);
	}
	*/
	public void disegnaCFG(PrintWriter x) {
		x.write("diGraph G {");
		x.println();
		Iterator<Edge<String>> eset=g.edges();
		while(eset.hasNext()) {
			Edge<String> e=eset.next();
			Vertex<String> u=e.getStartVertex();
			Vertex<String> v=e.getEndVertex();
			//nodo u
			x.write(u.getNomeNodo() + " [label=\"" + u.getNomeNodo() +" "+u.element() +" ");
			if(u.getVarAnn()!=null) {
				x.write("varAnnullate:"+ u.getVarAnn()+" " );
			}
			if(u.getVarUs()!=null) {
				x.write("varUsate:"+ u.getVarUs() +" " );
			}
			if(u.getVarDef()!=null) {
				x.write("varDefinite:"+ u.getVarDef()+" " );
			}
			x.write("\"]");
			x.println();
			
			//nodo v
			x.write(v.getNomeNodo() + " [label=\"" + v.getNomeNodo() +" "+v.element() +" ");
			if(v.getVarAnn()!=null) {
				x.write("varAnnullate:"+ v.getVarAnn()+" " );
			}
			if(v.getVarUs()!=null) {
				x.write("varUsate:"+ v.getVarUs() +" " );
			}
			if(v.getVarDef()!=null) {
				x.write("varDefinite:"+ v.getVarDef()+" " );
			}
			x.write("\"]");
			x.println();
			//arco
			x.write(u.getNomeNodo() + " -> " + v.getNomeNodo());
			if((e.element().equals("TRUE      "))||(e.element().equals("FALSE"))) {
				x.write(" [label=\""+e.element()+"\"]; ");
			}
			else if(e.element().equals("CAMMINO")) {
				x.write(" [color=\"blue\"]; ");
			}
			x.println();
		}
		x.write("\n}");
		x.println();
	}
	
	
	public static Graph<String> newGraph() {
		return new Graph<String>();
	}
	
	public static void addGraph(Graph<String> g) {
		grafiFunzione.add(g);
	}
	
	public boolean eseguiReachingDefinition(String variabile) {
		if(!(contains(g.getFirstNode().getVarAnn(),variabile))) {
			return false;
		}
		else {
			token=new Token(variabile);
			Iterator<Vertex<String>> iterator=g.vertices();
			while(iterator.hasNext()) {
				Vertex<String> v=iterator.next();
				if(v.getVarUs()!=null) {
					if(contains(v.getVarUs(),variabile)) {
						triple.add(new Tripla(token.nodo,v.getNomeNodo(),variabile));
					}
				}
				if(v.getVarDef()!=null) {
					if(v.getVarDef().equals(variabile)) {
						token.setNodo(v.getNomeNodo());
					}
				}
			}
			try {
				File f=new File("analisiReport.txt");
				FileWriter fw=new FileWriter(f);
				PrintWriter pw=new PrintWriter(fw,true);
				pw.println("Token: (" + token.var + "," + token.nodo +")");
				for(Tripla t:triple) {
					pw.println("    Tripla: "+t.toString());
				}
				pw.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	}
	
	public void tracciaCammini() {
		//String colori[]= {"blue","red","yellow","green","pink","brown","orange"};
		for(Tripla t:triple) {
			if(token.nodo.equals(t.nodo1)) {
				g.insertDirectedEdge(g.getNode(token.nodo), g.getNode(t.nodo2), "CAMMINO");
			}
		}
	}
		
	private boolean contains(String variabiliFunzione,String variabile) {
		String arg[]=variabiliFunzione.trim().split(" ");
		for(int i=0;i<arg.length;i++) {
			if(arg[i].equals(variabile)){
				return true;
			}
		}
		return false;
	}
	
	private class Tripla{
		private String var;
		private String nodo1;
		private String nodo2;
		
		private Tripla(String nodo1,String nodo2,String var){
			this.nodo1=nodo1;
			this.nodo2=nodo2;
			this.var=var;
		}
		public String toString() {
			return "(" +nodo1+","+nodo2+","+var+")";
		}
	}
	
	private class Token{
		private String nodo;
		private String var;
		
		private Token(String var) {
			this.var = var;
		}
		private void setNodo(String nodo) {
			this.nodo = nodo;
		}
	}
	
}

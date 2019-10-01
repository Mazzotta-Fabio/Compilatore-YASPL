package yasplcompiler;

import java.io.PrintWriter;
import java.util.*;
import javax.xml.stream.*;
import astcomponent.Programma;
import graphcomponent.*;

public class GraphGenerator {
	
	private Graph<String> g;
	private static ArrayList<Graph<String>> grafiFunzione;
	
	public GraphGenerator() {
		g=new Graph<String>();
		grafiFunzione=new ArrayList<Graph<String>>();
	}
	
	public void getControlFlowGraph(Programma p){
		p.buildControlFlow(g);
	}
	
	public void drawGraph(XMLStreamWriter x) throws XMLStreamException {
		for(Graph<String> grafo:grafiFunzione) {
			Iterator<Edge<String>>it=grafo.edges();
			while(it.hasNext()) {
				Edge<String> arco=it.next();
				Vertex<String> uu=arco.getStartVertex();
				Vertex<String> vv=arco.getEndVertex();
				x.writeStartElement(uu.getNomeNodo());
				x.writeAttribute("tipoIstruzione", uu.element());
				x.writeAttribute("variabiliIstruzione", uu.getInstruction());
				x.writeStartElement("ARCO");
				x.writeAttribute("TipoArco", arco.element());
				x.writeStartElement(vv.getNomeNodo());
				x.writeAttribute("tipoIstruzione", vv.element());
				x.writeAttribute("variabiliIstruzione", vv.getInstruction());
				x.writeEndElement();
				x.writeEndElement();
				x.writeEndElement();
			}
		}
		Iterator<Edge<String>> eset=g.edges();
		while(eset.hasNext()) {
			Edge<String> e=eset.next();
			Vertex<String> u=e.getStartVertex();
			Vertex<String> v=e.getEndVertex();
			x.writeStartElement(u.getNomeNodo());
			x.writeAttribute("tipoIstruzione", u.element());
			x.writeAttribute("variabiliIstruzione", u.getInstruction());
			x.writeStartElement("ARCO");
			x.writeAttribute("TipoArco", e.element());
			x.writeStartElement(v.getNomeNodo());
			x.writeAttribute("tipoIstruzione", v.element());
			x.writeAttribute("variabiliIstruzione", v.getInstruction());
			x.writeEndElement();
			x.writeEndElement();
			x.writeEndElement();
		}
	}
	
	public static Graph<String> newGraph() {
		return new Graph<String>();
	}
	
	public static void addGraph(Graph<String> g) {
		grafiFunzione.add(g);
	}
	
	public void eseguiReachingDefinition(PrintWriter c) {
		for(Graph<String> gr:grafiFunzione ) {
			Vertex<String> u=gr.getNode("ARGOMENTIFUNZIONE");
			Vertex<String> v=gr.getNode("DICHIARAZIONIVARIABILICORPOFUNZIONE");
			Vertex<String> z=gr.getNode("RETURNVALUEFUNCTION");
			String variabili[]=u.getInstruction().trim().split(" ");
			calcolaCammini(gr,c,variabili);
			if(v!=null) {
				String arg[]=v.getInstruction().trim().split(" ");
				calcolaCammini(gr,c,arg);
			}
			String variabile[]=new String[1];
			variabile[0]=z.getInstruction();
			c.write("La variabile "+ variabile[0] + " viene dichiarata al nodo " + gr.getFirstNode().getNomeNodo() +",");
			c.println();
			calcolaCammini(gr,c,variabile);
		}
		String variabili[]=g.getFirstNode().getInstruction().trim().split(" ");
		calcolaCammini(g,c,variabili);
		
	}
	private void calcolaCammini(Graph<String> grafo,PrintWriter c,String[]variabili) {
		for(int i=0;i<variabili.length;i++) {
			Iterator<Vertex<String>> iterator=grafo.vertices();
			while(iterator.hasNext()) {
				Vertex<String> v=iterator.next();
				switch(v.element()) {
				case "RETURNVALUEFUNCTION":
					if(contains(v.getInstruction(),variabili[i])) {
						c.write("       raggiunge l'uso al nodo "+v.getNomeNodo()+",");
					    c.println();
					}
					break;
				case "DICHIARAZIONIVARIABILI":
					if(contains(v.getInstruction(),variabili[i])) {
						c.write("La variabile "+variabili[i]+" viene dichiarata al nodo "+grafo.getFirstNode().getNomeNodo());
					    c.println();
					}
					break;
				case "DICHIARAZIONIVARIABILICORPOFUNZIONE":
					if(contains(v.getInstruction(),variabili[i])) {
						c.write("La variabile "+variabili[i]+" viene dichiarata al nodo "+v.getNomeNodo()+";");
						c.println();
					}
					break;
				case "ARGOMENTIFUNZIONE":
					if(contains(v.getInstruction(),variabili[i])) {
						c.write("La variabile " + variabili[i]+" viene usata come argomento della funzione " + grafo.getNode("DEFFUNZIONE").getInstruction() +" al nodo "+v.getNomeNodo());
						c.println();
					}
					break;
				case "READ":
					if(contains(v.getInstruction(),variabili[i])) {
						c.write("       Viene definita al nodo "+v.getNomeNodo()+",");
						c.println();
					}
					break;
				case "CALLOP":
					if(contains(v.getInstruction(),variabili[i])) {
						c.write("       raggiunge l'uso al nodo "+v.getNomeNodo() +";");
						c.println();
					}
					break;
				case "WRITE":
					if(contains(v.getInstruction(),variabili[i])) {
						c.write("       raggiunge l'uso al nodo "+v.getNomeNodo() +";");
						c.println();
					}
					break;
				case "ASSIGN":
					String [] arg=v.getInstruction().trim().split(" ");
					String id=arg[0].trim();
					for(int j=1;j<arg.length;j++) {
						if(arg[j].trim().equals(variabili[i])) {
							c.write("       raggiunge l'uso al nodo "+v.getNomeNodo() +";");
							c.println();
						}
					}
					if(id.equals(variabili[i])) {
						c.write("       Viene  definita al nodo "+v.getNomeNodo()+",");
						c.println();
					}
					break;
				default:
					if((v.element().substring(0,5).equals("IFTHEN"))&&(contains(v.getInstruction(),variabili[i]))) {
						c.write("       raggiunge l'uso al nodo "+v.getNomeNodo()+",");
						c.println();
					}
					else {
						if((v.element().contains("WHILE")) &&(contains(v.getInstruction(),variabili[i]))){
							c.write("       raggiunge l'uso al nodo "+v.getNomeNodo()+",");
							c.println();
						}
						else {
							if((v.element().contains("IFTHENELSE"))&&(contains(v.getInstruction(),variabili[i]))){
								c.write("       raggiunge l'uso al nodo "+v.getNomeNodo()+",");
								c.println();
							}
						}
					}
				}
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
}

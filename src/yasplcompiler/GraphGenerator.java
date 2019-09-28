package yasplcompiler;

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
		//return g;
	}
	
	public void drawGraph(XMLStreamWriter x) throws XMLStreamException {
		//Iterator<Vertex<String>> vset=g.vertices();
		for(Graph<String> grafo:grafiFunzione) {
			Iterator<Edge<String>>it=grafo.edges();
			while(it.hasNext()) {
				Edge<String> arco=it.next();
				Vertex<String> uu=arco.getStartVertex();
				Vertex<String> vv=arco.getEndVertex();
				x.writeStartElement(uu.getNomeNodo());
				x.writeAttribute("variabiliIstruzione", uu.element());
				x.writeAttribute("tipoIstruzione", uu.getInstruction());
				x.writeStartElement("ARCO");
				x.writeAttribute("TipoArco", arco.element());
				//x.
				//if(e.element().contains("STATEMENT")) {
					//x.writeEndElement();
				//}
				//else {
					x.writeStartElement(vv.getNomeNodo());
				    x.writeAttribute("variabiliIstruzione", vv.element());
				    x.writeAttribute("tipoIstruzione", vv.getInstruction());
				    x.writeEndElement();
				    x.writeEndElement();
				    x.writeEndElement();
			}
		}
		Iterator<Edge<String>> eset=g.edges();
		//Vertex<String>u=g.getFirstNode();
		//x.writeStartElement(u.getNomeNodo());
		//x.writeAttribute("tipoIstruzione", u.element());
		//x.writeAttribute("variabiliIstruzioni",u.getInstruction());
		while(/*(vset.hasNext())||*/eset.hasNext())/*))*/ {
			//Vertex<String> u=vset.next();
			//x.writeStartElement(u.getNomeNodo());
			//x.writeAttribute("tipoIstruzione", u.element());
			//x.writeAttribute("variabiliIstruzioni",u.getInstruction());
			Edge<String> e=eset.next();
			Vertex<String> u=e.getStartVertex();
			Vertex<String> v=e.getEndVertex();
			x.writeStartElement(u.getNomeNodo());
			x.writeAttribute("variabiliIstruzione", u.element());
			x.writeAttribute("tipoIstruzione", u.getInstruction());
			x.writeStartElement("ARCO");
			x.writeAttribute("TipoArco", e.element());
			//x.
			//if(e.element().contains("STATEMENT")) {
				//x.writeEndElement();
			//}
			//else {
				x.writeStartElement(v.getNomeNodo());
			    x.writeAttribute("variabiliIstruzione", v.element());
			    x.writeAttribute("tipoIstruzione", v.getInstruction());
			    x.writeEndElement();
			    x.writeEndElement();
			    x.writeEndElement();
			//}
		}
		/*
		while(eset.hasNext()) {
			
			Vertex<String> u=e.getStartVertex();
			Vertex<String> v=e.getEndVertex();
			x.writeStartElement(u.getNomeNodo());
			x.writeAttribute("tipoIstruzione", u.element());
			x.writeAttribute("variabiliIstruzioni",u.getInstruction());
			x.
		}
		*/
	}
	
	public static Graph<String> newGraph() {
		return new Graph<String>();
	}
	
	public static void addGraph(Graph<String> g) {
		grafiFunzione.add(g);
	}
}

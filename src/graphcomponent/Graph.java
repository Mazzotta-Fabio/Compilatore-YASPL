package graphcomponent;

import java.util.*;

public class Graph<E> {
	private int numVer;//numero di vertici
	private int numEdg;//numero di archi
	private PositionList<Vertex<E>> VList; //lista dei vertici
	private PositionList<Edge<E>> EList; //lista degli archi
	private MapTable<Boolean,E> lastConds;
	private Vertex<E> lastNodeIfelseFalse;
	private Vertex<E> lastNodeIfelseTrue;
	
	public Graph() {
		numVer=0;
		numEdg=0;
		VList=new NodePositionList<Vertex<E>>();
		EList=new NodePositionList<Edge<E>>();
		lastConds=new MapTable<Boolean,E>();
		lastNodeIfelseFalse=null;
		lastNodeIfelseTrue=null;
	}
	
	public Vertex<E> insertVertex(E info) {
		numVer++;
		Vertex<E> v = new Vertex<E>(info,"NODO"+numVer);
		VList.addLast(v);
		return v;
	}
	
	public Edge<E> insertDirectedEdge(Vertex <E>u, Vertex <E>v,E info) {
		numEdg++;
		Edge<E>e = new Edge<E>(u,v,info);	
		EList.addLast(e);
		u.insertAdjacent(v);
		return e;
	}	
	public Iterator<Vertex<E>> vertices(){
		return VList.iterator();
	}
	
	public Iterator<Edge<E>> edges(){
		return EList.iterator();
	}
	
	public Iterator<Vertex<E>> adjacentVertex(Vertex<E> v){
		return v.getAdjList().iterator();
	}
	
	public Vertex<E> getFirstNode(){
		return VList.first().element();
	}
	public Vertex<E>getLastNode(){
		return VList.last().element();
	}
	
	public void addLastCond(E lastCond) {
		this.lastConds.put( true,lastCond);
	}
	
	public int getNumeVer() {
		return numVer;
	}
	
	public void changeNameEdge(E tipoArco) {
		Iterator<Edge<E>> eset=edges();
		while(eset.hasNext()) {
			Edge<E> e=eset.next();
			Vertex<E> x=e.getStartVertex();
			if(lastConds.containsValue(x.element())){
				e.setInfo(tipoArco);
			}
		}
	}
	
	public void changeStatement(E e) {
		lastConds.changeElement(false,e);
	}
	
	public void setLastNodeIfelseFalse(Vertex<E> e) {
		lastNodeIfelseFalse=e;
	}
	
	public void setLastNodeIfElseTrue(Vertex<E> e) {
		lastNodeIfelseTrue=e;
	}
	
	@SuppressWarnings("unchecked")
	public boolean setEdgeFalseStatement(Vertex<E>v) {
		boolean flag=false;
		if(lastNodeIfelseTrue!=null) {
			insertDirectedEdge(lastNodeIfelseTrue,v,(E)("NORMAL"));
			lastNodeIfelseTrue=null;
	    }
		if(lastConds.containsKey(false)) {
			Vertex<E> u=getNode(lastConds.get(false));
			insertDirectedEdge(u,v,(E)("FALSE"));
			if(lastNodeIfelseFalse!=null) {
				insertDirectedEdge(lastNodeIfelseFalse,v,(E)("NORMAL"));
				lastNodeIfelseFalse=null;
		    }
			lastConds.remove(false);
			flag=true;
		}
		if(flag) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Vertex<E> getNode(E e){
		Iterator<Vertex<E>> iterator=vertices();
		while(iterator.hasNext()) {
			Vertex<E> v=iterator.next();
			if(v.element().equals(e)) {
				return v;
			}
			if(v.getNomeNodo().equals(e)) {
				return v;
			}
		}
		return null;
	}
}
package graphcomponent;

import java.util.ArrayList;

public class Vertex<E> implements Position<E>{
	
	private E info;
	private ArrayList<E> varsAnn;
	private E varDef;
	private ArrayList<E> varsUs;
	private String nameNode;
	private NodePositionList<Vertex<E>> adjList;
	
	public Vertex(E info,String nameNode) {
		varsUs=new ArrayList<E>();
		varDef=null;
		varsAnn=new ArrayList<E>();
		this.info=info;
		this.nameNode=nameNode;
		adjList=new NodePositionList<Vertex<E>>();
	}
	@Override
	public E element() {
		return info;
	}
	
	public void insertAdjacent(Vertex<E> v) {
		adjList.addLast(v);
	}
	public NodePositionList<Vertex<E>> getAdjList() {
		return adjList;
	}
	public String getNomeNodo() {
		return nameNode;
	}
	public E getVarDef() {
		return varDef;
	}
	public void setVarDef(E varDef) {
		this.varDef = varDef;
	}
	
	public String getVarUs() {
		if(varsUs.size()>0) {
			String frase=""+varsUs.get(0);
			for(int i=1;i<varsUs.size();i++) {
				frase=frase+" "+varsUs.get(i);
			}
			return frase;
		}
		return null;
	}
	
	public void addVarUs(E varUs) {
		varsUs.add(varUs);
	}
	
	public String getVarAnn() {
		if(varsAnn.size()>0) {
			String frase=""+varsAnn.get(0);
			for(int i=1;i<varsAnn.size();i++) {
				frase=varsAnn.get(i)+" "+frase;
			}
			return frase;
		}
		return null;
	}
	
	public void addVarAnn(E varAnn) {
		varsAnn.add(varAnn);
	}
}

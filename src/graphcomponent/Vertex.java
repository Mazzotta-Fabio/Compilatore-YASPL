package graphcomponent;

public class Vertex<E> implements Position<E>{
	
	private E info;
	private E typeIstruction;
	private String nameNode;
	private NodePositionList<Vertex<E>> adjList;
	
	public Vertex(E info,E typeInstruction,String nameNode) {
		this.typeIstruction=typeInstruction;
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
	public void setInstruction(E info) {
		this.typeIstruction = info;
	}
	public NodePositionList<Vertex<E>> getAdjList() {
		return adjList;
	}
	public String getNomeNodo() {
		return nameNode;
	}
	public E getInstruction() {
		return typeIstruction;
	}
	
}

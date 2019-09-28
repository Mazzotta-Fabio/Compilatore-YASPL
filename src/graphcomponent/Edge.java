package graphcomponent;

public class Edge <E>implements Position<E>{
	
	private E info;
	private Vertex<E> startVertex;
	private Vertex<E> endVertex;
	
	public E element() {
		return info;
	}
	
	public Edge(Vertex<E> st,Vertex<E> end,E e) {
		info=e;
		startVertex=st;
		endVertex=end;
	}

	public void setInfo(E info) {
		this.info = info;
	}

	public Vertex<E> getStartVertex() {
		return startVertex;
	}

	public Vertex<E> getEndVertex() {
		return endVertex;
	}
	
	
}

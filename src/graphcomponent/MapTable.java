package graphcomponent;

import java.util.ArrayList;

public class MapTable<K,V> {
	
	private ArrayList<Element> elementi;
	
	public MapTable() {
		elementi=new ArrayList<Element>();
	}
	
	public void put(K k,V v) {
		elementi.add(new Element(k,v));
	}
	
	public void changeElement(K k,V v) {
		for(int i=0;i<elementi.size();i++) {
			if(elementi.get(i).getValue().equals(v)) {
				elementi.get(i).setKey(k);
			}
		}
	}
	
	public void remove (K v) {
		for(int i=0;i<elementi.size();i++) {
			if(elementi.get(i).getKey().equals(v)) {
				elementi.remove(i);
			}
		}
	}
	
	//containsValue
	public boolean containsValue(V v) {
		for(Element e:elementi) {
			if(e.getValue().equals(v)) {
				return true;
			}
		}
		return false;
	}
	
	//get
	public V get(K k) {
		for(Element e:elementi) {
			if(e.getKey().equals(k)) {
				return e.getValue();
			}
		}
		return null;
	}
	
	//containsKey
	public boolean containsKey(K k) {
		for(Element e:elementi) {
			if(e.getKey().equals(k)) {
				return true;
			}
		}
		return false;
	}
	class Element{
		private K key;
		private V value;
		
		public Element(K key,V value) {
			this.key=key;
			this.value=value;
		}
		
		public K getKey() {
			return key;
		}
		public V getValue() {
			return value;
		}
		public void setKey(K k) {
			key=k;
		}
	}
}

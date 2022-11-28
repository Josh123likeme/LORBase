package me.Josh123likeme.LORBase.Types;

public class Pair<T, U> {

	T first;
	U last;
	
	public Pair(T first, U last) {
		
		this.first = first;
		this.last = last;
		
	}
	
	public T getFirst() {
		
		return first;
		
	}
	public U getLast() {
		
		return last;
		
	}
	
}

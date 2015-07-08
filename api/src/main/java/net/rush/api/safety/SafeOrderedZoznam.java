package net.rush.api.safety;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class SafeOrderedZoznam<Value> extends SafetyWarnings implements Iterable<Value> {

	protected List<Value> list = new ArrayList<>();
	
	public SafeOrderedZoznam() {}
	
	public SafeOrderedZoznam(List<Value> list) {
		this.list = list;
	}
	
	public boolean add(Value e) {
		if (contains(e))
			warn("List already contains: " + e);
		
		return list.add(e);
	}

	public void clear() {
		list.clear();
	}
	
	// Object -> String
	public boolean contains(Value o) {		
		return list.contains(o);
	}

	public Value get(int index) {
		if(list.get(index) == null)
			warn("Value on index " + index + " is null!");
		
		return list.get(index);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public Iterator<Value> iterator() {
		return list.iterator();
	}

	// Object -> String
	public boolean remove(Value o) {
		if (!contains(o))
			warn("List does not contain " + o + ", no removal!");
		
		return list.remove(o);
	}

	public Value remove(int index) {
		return list.remove(index);
	}

	public Value set(int index, Value element) {
		return list.set(index, element);
	}

	public int size() {		
		return list.size();
	}
	
	public String listValues() {
		String array = "";
		
		for(Value val : list) {
			if (array.isEmpty())
				array = val.toString();
			else
				array = array + ", " + val;
		}
			
		return array;
	}
	
	@Override
	public SafeOrderedZoznam<Value> showWarnings() {
		warn = true;		
		return this;
	}
	
	@Override
	public SafeOrderedZoznam<Value> setReadOnly() {
		readOnly = true;		
		return this;
	}
}

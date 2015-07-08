package net.rush.api.safety;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class SafeUnorderedZoznam<Value> extends SafetyWarnings implements Iterable<Value> {

	private Set<Value> list = new HashSet<>();

	public SafeUnorderedZoznam() {
	}

	public SafeUnorderedZoznam(Set<Value> zbierka) {
		list = zbierka;
	}
	
	public SafeUnorderedZoznam(SafeUnorderedZoznam<Value> zbierka) {
		list.addAll(zbierka.getSetAsArray());
	}

	public boolean add(Value e) {
		if (contains(e))
			warn("Value already in the set: " + e);
		
		return list.add(e);
	}
	
	public boolean addAll(Collection<? extends Value> c) {
		if (list.containsAll(c))
			warn("Values already in the set: " + c);
		
		return list.addAll(c);
	}

	public void clear() {
		list.clear();
	}

	public boolean contains(Value o) {
		return list.contains(o);
	}
	
	@Override
	public Iterator<Value> iterator() {
		return list.iterator();
	}

	public boolean remove(Value o) {
		if (!contains(o))
			warn("Cannot remoove since set does not contains: " + o);

		return list.remove(o);
	}

	public int size() {
		return list.size();
	}
	
	public Value[] toArray() {
		return (Value[]) list.toArray();
	}

	public void fillWith(Collection<? extends Value> values) {
		list.addAll(values);
	}

	public List<Value> getSetAsArray() {
		return new ArrayList<>(list);
	}

	@Override
	public SafeUnorderedZoznam<Value> showWarnings() {
		warn = true;
		return this;
	}
	
	@Override
	public SafeUnorderedZoznam<Value> setReadOnly() {
		readOnly = true;		
		return this;
	}
}

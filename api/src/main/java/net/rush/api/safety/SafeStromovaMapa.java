package net.rush.api.safety;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SafeStromovaMapa<Key, Value> extends SafetyWarnings {

	private TreeMap<Key, Value> map = new TreeMap<>();

	public Value put(Key key, Value value) {
		if (containsKey(key))
			warn("Already contains key: " + key + " with value: " + value);
		
		return map.put(key, value);
	}

	public void clear() {
		map.clear();
	}

	// Object -> String
	public boolean containsKey(Key key) {
		return map.containsKey(key);
	}
	
	// Object -> String
	public Value remove(Key key) {
		if (!containsKey(key))
			warn("Cannot remove key " + key + " since map does not contains it!");
		
		return map.remove(key);
	}
	
	public int size() {
		return map.size();
	}

	public Value get(Key key) {
		if(!containsKey(key))
			warn("TreeMap doesnt contains key: " + key);
			
		return map.get(key);
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Collection<Value> values() {
		return map.values();
	}

	public Set<Key> keySet() {
		return map.keySet();
	}

	public Map.Entry<Key, Value> lastEntry() {
		return map.lastEntry();
	}
	
	public Key lastKey() {
		return map.lastKey();
	}
	
	public Map.Entry<Key, Value> firstEntry() {
		return map.firstEntry();
	}
	
	public Key firstKey() {
		return map.firstKey();
	}
	
	public String listValues() {
		String array = "";
		int i = 0;
		
		for(Key key : keySet()) {
			if (i == 0)
				array = key.toString();
			else
				array = array + ", " + key;
			i++;
		}
			
		return array;
	}
	
	public Set<java.util.Map.Entry<Key, Value>> entrySet() {
		return map.entrySet();
	}
	
	@Override
	public SafeStromovaMapa<Key, Value> showWarnings() {
		warn = true;		
		return this;
	}
	
	@Override
	public SafeStromovaMapa<Key, Value> setReadOnly() {
		readOnly = true;		
		return this;
	}
}

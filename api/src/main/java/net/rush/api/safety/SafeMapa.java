package net.rush.api.safety;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class SafeMapa<Key, Value> extends SafetyWarnings {

	private HashMap<Key, Value> map = new HashMap<>();

	public Value put(Key key, Value value) {
		if (containsKey(key))
			warn("Already contains key: " + key + " with value: " + value);

		return map.put(key, value);
	}
	
	public Value putIfAbsent(Key key, Value value) {
		return map.putIfAbsent(key, value);
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
			warn("Key does not exist: " + key);

		return map.remove(key);
	}

	public int size() {
		return map.size();
	}

	public Value get(Key key) {
		if (map.get(key) == null)
			warn("Cannot get a key since it does not exist: " + key);

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

	public String listValues() {
		String array = "";
		int i = 0;

		for (Key key : keySet()) {
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
	public SafeMapa<Key, Value> showWarnings() {
		warn = true;
		return this;
	}
	
	@Override
	public SafeMapa<Key, Value> setReadOnly() {
		readOnly = true;		
		return this;
	}
}

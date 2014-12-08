package net.rush.api.meta;


import java.util.Objects;

import net.rush.api.ItemStack;
import net.rush.api.Position;

/**
 * Represents a single parameter - this is used for things like mob metadata.
 * @param <T> The type of value this parameter contains.
 */
public class MetaParam<T> {

	/**
	 * The maximum number of parameters in each group.
	 */
	public static final int METADATA_SIZE = 32;

	public enum Type {
		BYTE,
		SHORT,
		INT,
		FLOAT,
		STRING,
		ITEM,
		COORDINATE;
		
		public int getId() {
			return ordinal();
		}
		
		public static Type fromId(int id) {
			Type type = values()[id];
			Objects.requireNonNull(type, "Unknown metadata id " + id);
			
			return type;
		}
	}
	
	private final Type type;
	private final int index;
	private final T value;
	
	public MetaParam(int index, T value) {
		this.type = typeFromValue(value);
		this.index = index;
		this.value = value;
	}
	
	public Type getType() {
		return type;
	}
	
	public int getIndex() {
		return index;
	}
	
	public T getValue() {
		return value;
	}
	
	public static Type typeFromValue(Object value) {
		if (value instanceof Byte)
			return Type.BYTE;
		else if (value instanceof Short)
			return Type.SHORT;
		else if (value instanceof Integer)
			return Type.INT;
		else if (value instanceof Float)
			return Type.FLOAT;
		else if (value instanceof String)
			return Type.STRING;
		else if (value instanceof ItemStack)
			return Type.ITEM;
		else if (value instanceof Position)
			return Type.COORDINATE;
		
		throw new NullPointerException("Unknown metadata type: " + value.getClass().getTypeName());
	}
	
	@Override
	public String toString() {
		return "Metadata{type= " + type + ",index=" + index + ",value=" + value + "}";
	}
}


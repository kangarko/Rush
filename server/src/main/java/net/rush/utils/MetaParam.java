package net.rush.utils;


import lombok.Getter;
import net.rush.api.ItemStack;
import net.rush.model.Position;

/**
 * Represents a single parameter - this is used for things like mob metadata.
 * @param <T> The type of value this parameter contains.
 */
@Getter
public class MetaParam<T> {

	/**
	 * The maximum number of parameters in each group.
	 */
	public static final int METADATA_SIZE = 32;

	public static final int TYPE_BYTE = 0;
	public static final int TYPE_SHORT = 1;
	public static final int TYPE_INT = 2;
	public static final int TYPE_FLOAT = 3;
	public static final int TYPE_STRING = 4;
	public static final int TYPE_ITEM = 5;
	public static final int TYPE_COORDINATE = 6;
	
	private final int type;
	private final int index;
	private final T value;
	
	public MetaParam(int index, T value) {
		this.type = typeFromValue(value);
		this.index = index;
		this.value = value;
	}

	public static String typeToString(int type) {
		switch (type) {
			case TYPE_BYTE:
				return "byte";
			case TYPE_SHORT:
				return "short";
			case TYPE_INT:
				return "int";
			case TYPE_FLOAT:
				return "float";
			case TYPE_STRING:
				return "string";
			case TYPE_ITEM:
				return "item";
			case TYPE_COORDINATE:
				return "coordinate";
			default:
				throw new NullPointerException("Unknown metadata ID " + type);
		}
	}
	
	public static int typeFromValue(Object value) {
		if (value instanceof Byte)
			return TYPE_BYTE;
		else if (value instanceof Short)
			return TYPE_SHORT;
		else if (value instanceof Integer)
			return TYPE_INT;
		else if (value instanceof Float)
			return TYPE_FLOAT;
		else if (value instanceof String)
			return TYPE_STRING;
		else if (value instanceof ItemStack)
			return TYPE_ITEM;
		else if (value instanceof Position)
			return TYPE_COORDINATE;
		throw new NullPointerException("Unknown metadata type: " + value.getClass().getTypeName());
	}
	
	@Override
	public String toString() {
		return "Metadata {type = " + typeToString(type) + ", index = " + index + ", value = " + value + "}";
	}
}


package net.rush.packets.serialization;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.rush.model.Coordinate;
import net.rush.model.Item;
import net.rush.packets.NetUtils;
import net.rush.packets.misc.MetadataType;
import net.rush.util.Parameter;



public enum Type {
	INT(new Serializor<Integer>() {
		@Override
		public Integer read(DataInput in) throws IOException {
			return in.readInt();
		}

		@Override
		public void write(DataOutput out, Integer val) throws IOException {
			out.writeInt(val);
		}
	}),
	BYTE(new Serializor<Byte>() {
		@Override
		public Byte read(DataInput in) throws IOException {
			return in.readByte();
		}

		@Override
		public void write(DataOutput out, Byte val) throws IOException {
			out.writeByte(val);
		}
	}),
	UNSIGNED_BYTE(new Serializor<Integer>() {
		@Override
		public Integer read(DataInput in) throws IOException {
			return in.readUnsignedByte();
		}

		@Override
		public void write(DataOutput out, Integer val) throws IOException {
			out.writeByte(val);
		}
	}),
	BOOL(new Serializor<Boolean>() {
		@Override
		public Boolean read(DataInput in) throws IOException {
			return in.readBoolean();
		}

		@Override
		public void write(DataOutput out, Boolean val) throws IOException {
			out.writeBoolean(val);
		}
	}),
	STRING(new Serializor<String>() {
		@Override
		public String read(DataInput in) throws IOException {
			return NetUtils.readString(in, 1000);
		}

		@Override
		public void write(DataOutput out, String val) throws IOException {
			NetUtils.writeString(out, val);
		}
	}),
	SHORT(new Serializor<Short>() {
		@Override
		public Short read(DataInput in) throws IOException {
			return in.readShort();
		}

		@Override
		public void write(DataOutput out, Short val) throws IOException {
			out.writeShort(val);
		}
	}),
	UNSIGNED_SHORT(new Serializor<Integer>() {
		@Override
		public Integer read(DataInput in) throws IOException {
			return in.readUnsignedShort();
		}

		@Override
		public void write(DataOutput out, Integer val) throws IOException {
			out.writeShort(val);
		}
	}),
	BYTE_ARRAY(new ObjectUsingSerializor<byte[]>() {
		@Override
		public byte[] read(DataInput in, Object moreInfo) throws IOException {
			byte[] bytes = new byte[((Number) moreInfo).intValue()];
			in.readFully(bytes);
			return bytes;
		}

		@Override
		public void write(DataOutput out, byte[] val) throws IOException {
			out.write(val);
		}
	}),
	FLOAT(new Serializor<Float>() {
		@Override
		public Float read(DataInput in) throws IOException {
			return in.readFloat();
		}

		@Override
		public void write(DataOutput out, Float val) throws IOException {
			out.writeFloat(val);
		}
	}),
	DOUBLE(new Serializor<Double>() {
		@Override
		public Double read(DataInput in) throws IOException {
			return in.readDouble();
		}

		@Override
		public void write(DataOutput out, Double val) throws IOException {
			out.writeDouble(val);
		}
	}),
	LONG(new Serializor<Long>() {
		@Override
		public Long read(DataInput in) throws IOException {
			return in.readLong();
		}

		@Override
		public void write(DataOutput out, Long val) throws IOException {
			out.writeLong(val);
		}
	}),
	ENTITY_METADATA(new Serializor<Parameter<?>[]>() {
		@Override
		public Parameter<?>[] read(DataInput in) throws IOException {
			Parameter<?>[] parameters = new Parameter<?>[Parameter.METADATA_SIZE];
			for (int x = in.readUnsignedByte(); x != 127; x = in.readUnsignedByte()) {
				int index = x & 0x1F;
				int type = x >> 5;
				MetadataType metaType = MetadataType.fromId(type);
				switch (metaType) {
				case BYTE:
					//data.put(index, new GenericMetadata<Byte>(in.readByte(), metaType));
					parameters[index] = new Parameter<Byte>(type, index, in.readByte());
					break;
				case SHORT:
					parameters[index] = new Parameter<Short>(type, index, in.readShort());
					//data.put(index, new GenericMetadata<Short>(in.readShort(), metaType));
					break;
				case INT:
					parameters[index] = new Parameter<Integer>(type, index, in.readInt());
					//data.put(index, new GenericMetadata<Integer>(in.readInt(), metaType));
					break;
				case FLOAT:
					parameters[index] = new Parameter<Float>(type, index, in.readFloat());
					//data.put(index, new GenericMetadata<Float>(in.readFloat(), metaType));
					break;
				case STRING:
					parameters[index] = new Parameter<String>(type, index, readUtf8String(in));
					//data.put(index, new GenericMetadata<String>(NetUtils.readString(in, 1000), metaType));
					break;
				case ITEM:
					short id = in.readShort();
					if (id <= 0) {
						parameters[index] = new Parameter<Item>(type, index, Item.NULL_ITEM);
					} else {
						byte stackSize = in.readByte();
						short dataValue = in.readShort();
						//short dataLenght = in.readShort();
						//byte[] metadata = new byte[0];
						//if(dataLenght > 0) {
							// FIXME previous check if its enchantable
							//metadata = new byte[dataLenght];
							//in.readFully(metadata);
						//}
						parameters[index] = new Parameter<Item>(type, index, new Item(id, stackSize, dataValue));
					}
					//data.put(index, new GenericMetadata<Item>(new Item(id, count, damage), metaType));
					break;
				default:
					throw new UnsupportedOperationException("Metadata-type '" + metaType + "' is not implemented!");
				}
			}
			return parameters;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void write(DataOutput out, Parameter<?>[] val) throws IOException {
			for (Parameter<?> parameter : val) {

				if (parameter == null)
					continue;

				int type  = parameter.getType();
				int index = parameter.getIndex();

				out.writeByte(((type & 0x07) << 5) | (index & 0x1F));

				switch (type) {
				case Parameter.TYPE_BYTE:
					out.writeByte(((Parameter<Byte>) parameter).getValue());
					break;
				case Parameter.TYPE_SHORT:
					out.writeShort(((Parameter<Short>) parameter).getValue());
					break;
				case Parameter.TYPE_INT:
					out.writeInt(((Parameter<Integer>) parameter).getValue());
					break;
				case Parameter.TYPE_FLOAT:
					out.writeFloat(((Parameter<Float>) parameter).getValue());
					break;
				case Parameter.TYPE_STRING:
					writeUtf8String(out, ((Parameter<String>) parameter).getValue());
					break;
				case Parameter.TYPE_ITEM:
					Item item = ((Parameter<Item>) parameter).getValue();

					if (item.getId() <= 0) { // FIXME less then zero check
						out.writeShort(-1);
					} else {
						out.writeShort(item.getId());
						out.writeByte(item.getCount());
						out.writeShort(item.getDamage());
						out.writeShort(-1);
						//if (item.getDataLength() >= 0) { // FIXME previous check if its enchantable
						//	out.write(item.getMetadata());
						//}
					}
					break;
				case Parameter.TYPE_COORDINATE:
					Coordinate coord = ((Parameter<Coordinate>) parameter).getValue();
					out.writeInt(coord.getX());
					out.writeInt(coord.getY());
					out.writeInt(coord.getZ());
					break;
				}
			}
			out.writeByte(127);
		}
	}),
	ITEM(new Serializor<Item>() {
		@Override
		public Item read(DataInput in) throws IOException {
			short id = in.readShort();
			if (id <= 0) {
				return Item.NULL_ITEM;
			} else {
				byte stackSize = in.readByte();
				short dataValue = in.readShort();
				/*short dataLenght = in.readShort();
				byte[] metadata = new byte[0];
				if(dataLenght >= 0) {
					if (id != 0) { // FIXME previous check if its enchantable
						metadata = new byte[dataLenght];
						in.readFully(metadata);
					}
				}*/
				return new Item(id, stackSize, dataValue);
			}
		}

		@Override
		public void write(DataOutput out, Item val) throws IOException {
			if (val == Item.NULL_ITEM || val.getId() <= 0) { // FIXME less then zero check
				out.writeShort(-1);
			} else {
				out.writeShort(val.getId());
				out.writeByte(val.getCount());
				out.writeShort(val.getDamage());
				out.writeShort(-1);
				/*if (val.getDataLength() != 0) { // FIXME previous check if its enchantable // TODO is is Id or datalength?
					out.write(val.getMetadata());
				}*/
			}
		}
	}),
	@SuppressWarnings({ "unchecked", "rawtypes" })
	INT_ARRAY(new ObjectUsingSerializor<int[]>() {
		@Override
		public int[] read(DataInput in, Object more) throws IOException {
			int count = ((Number) more).intValue();
			int[] array = new int[count];
			Serializor<Integer> intserializator = (Serializor<Integer>) INT.getSerializor();
			for (int i = 0; i < count; i++) {
				array[i] = intserializator.read(in);
			}
			return array;
		}

		@Override
		public void write(DataOutput out, int[] val) throws IOException {
			Serializor intSerializor = INT.getSerializor();
			for (int i = 0; i < val.length; i++) {
				intSerializor.write(out, val[i]);
			}
		}
	}),
	@SuppressWarnings({ "unchecked", "rawtypes" })
	ITEM_ARRAY(new ObjectUsingSerializor<Item[]>() {
		@Override
		public Item[] read(DataInput in, Object more) throws IOException {
			int count = ((Number) more).intValue();
			Item[] items = new Item[count];
			Serializor<Item> itemSerializor = (Serializor<Item>) ITEM.getSerializor();
			for (int i = 0; i < count; i++) {
				items[i] = itemSerializor.read(in);
			}
			return items;
		}

		@Override
		public void write(DataOutput out, Item[] val) throws IOException {
			Serializor itemSerializor = ITEM.getSerializor();
			for (int i = 0; i < val.length; i++) {
				itemSerializor.write(out, val[i]);
			}
		}
	}),
	CONDITIONAL_SHORT(new ObjectUsingSerializor<Short>() {
		/*
		 * ugly hack, but I don't see a better solution :/
		 */
		@Override
		public Short read(DataInput in, Object moreInfo) throws IOException {
			if (((Number) moreInfo).intValue() > 0) {
				return in.readShort();
			} else {
				return Short.MIN_VALUE;
			}
		}
		@Override
		public void write(DataOutput out, Short val) throws IOException {
			if (val != Short.MIN_VALUE)
				out.writeShort(val);
		}
	}),
	BLOCKCOORD_COLLECTION(new Serializor<Collection<Coordinate>>() {
		@Override
		public Collection<Coordinate> read(DataInput in) throws IOException {
			int size = in.readInt();
			Set<Coordinate> ret = new HashSet<Coordinate>();
			for (int i = 0; i < size; i++) {
				int x = in.readByte();
				int y = in.readByte();
				int z = in.readByte();
				ret.add(new Coordinate(x, y, z));
			}
			return ret;
		}

		@Override
		public void write(DataOutput out, Collection<Coordinate> val) throws IOException {
			out.writeInt(val.size());
			for (Coordinate block : val) {
				out.writeByte(block.getX());
				out.writeByte(block.getY());
				out.writeByte(block.getZ());
			}
		}
	});

	/*private static final int[] ENCHANTABLE_ITEMS = {
		// see http://wiki.vg/Slot_Data#Enchantable_items
		0x103, 0x105, 0x15A, 0x167, // flint'n'steel, bow, fishingRod, shears
		// TOOLS
		// sword, shovel, pickaxe, axe, hoe
		0x10C, 0x10D, 0x10E, 0x10F, 0x122, // WOOD
		0x110, 0x111, 0x112, 0x113, 0x123, // STONE
		0x10B, 0x100, 0x101, 0x102, 0x124, // IRON
		0x114, 0x115, 0x116, 0x117, 0x125, // DIAMOND
		0x11B, 0x11C, 0x11D, 0x11E, 0x126, // GOLD
		// ARMOUR
		// helmet, chestplate, leggings, boots
		0x12A, 0x12B, 0x12C, 0x12D, // LEATHER
		0x12E, 0x12F, 0x130, 0x131, // CHAIN
		0x132, 0x133, 0x134, 0x135, // IRON
		0x136, 0x137, 0x138, 0x139, // DIAMOND
		0x13A, 0x13B, 0x13C, 0x13D  // GOLD
	};

	private static boolean isEnchantable(int id) {
		for (int i = 0; i < ENCHANTABLE_ITEMS.length; i++) {
			if (ENCHANTABLE_ITEMS[i] == id)
				return true;
		}
		return false;
	}*/

	private final Serializor<?> serializor;

	Type(Serializor<?> serializor) {
		this.serializor = serializor;
	}

	public Serializor<?> getSerializor() {
		return serializor;
	}

	private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

	/**
	 * Reads a UTF-8 encoded string from the buffer.
	 * @param buf The buffer.
	 * @return The string.
	 */
	public static String readUtf8String(DataInput buf) throws IOException {
		int len = buf.readUnsignedShort();

		byte[] bytes = new byte[len];
		buf.readFully(bytes);

		return new String(bytes, CHARSET_UTF8);
	}

	/**
	 * Writes a UTF-8 string to the buffer.
	 * @param buf The buffer.
	 * @param str The string.
	 * @throws IllegalArgumentException if the string is too long
	 * <em>after</em> it is encoded.
	 */
	public static void writeUtf8String(DataOutput buf, String str) throws IOException {
		byte[] bytes = str.getBytes(CHARSET_UTF8);
		if (bytes.length >= 65536) {
			throw new IllegalArgumentException("Encoded UTF-8 string too long.");
		}

		buf.writeShort(bytes.length);
		buf.write(bytes);
	}

}

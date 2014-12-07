package net.rush.protocol;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

import net.rush.api.ItemStack;
import net.rush.exceptions.PacketException;
import net.rush.model.Position;
import net.rush.utils.MetaParam;

import org.apache.commons.lang3.Validate;

public class Packet {

	public static void writeString(String s, ByteBuf buf) {
		Validate.isTrue(s.length() <= Short.MAX_VALUE, "Cannot send string longer than Short.MAX_VALUE (got %s characters)", s.length());

		byte[] b = s.getBytes(StandardCharsets.UTF_8);
		writeVarInt(b.length, buf);
		buf.writeBytes(b);
	}

	public static String readString(ByteBuf buf) {
		int len = readVarInt(buf);
		Validate.isTrue(len <= Short.MAX_VALUE, "Cannot receive string longer than Short.MAX_VALUE (got %s characters)", len);

		byte[] b = new byte[len];
		buf.readBytes(b);

		return new String(b, StandardCharsets.UTF_8);
	}

	public static void writeArray(byte[] b, ByteBuf buf) {
		writeVarInt(b.length, buf);
		buf.writeBytes(b);
	}

	public static byte[] readArray(ByteBuf buf) {
		byte[] ret = new byte[readVarInt(buf)];
		buf.readBytes(ret);
		
		return ret;
	}

	public static void writeStringArray(String[] s, ByteBuf buf) {
		writeVarInt(s.length, buf);
		
		for (String str : s)
			writeString(str, buf);		
	}

	public static String[] readStringArray(ByteBuf buf) {
		int len = readVarInt(buf);
		String[] ret = new String[len];
		
		for (int i = 0; i < ret.length; i++) 
			ret[i] = readString(buf);
		
		return ret;
	}

	public static int readVarInt(ByteBuf input) {
		return readVarInt(input, 5);
	}

	public static int readVarInt(ByteBuf input, int maxBytes) {
		int out = 0;
		int bytes = 0;
		byte in;
		
		while (true) {
			in = input.readByte();

			out |= (in & 0x7F) << (bytes++ * 7);

			if (bytes > maxBytes)
				throw new RuntimeException("VarInt too big");			

			if ((in & 0x80) != 0x80)
				break;
		}

		return out;
	}

	public static void writeVarInt(int value, ByteBuf output) {
		int part;
		
		while (true) {
			part = value & 0x7F;

			value >>>= 7;
		
			if (value != 0)
				part |= 0x80;

			output.writeByte(part);

			if (value == 0)
				break;
		}
	}

	public static int readVarShort(ByteBuf buf) {
		int low = buf.readUnsignedShort();
		int high = 0;
		
		if ((low & 0x8000) != 0) {
			low = low & 0x7FFF;
			high = buf.readUnsignedByte();
		}
		
		return ((high & 0xFF) << 15) | low;
	}

	public static void writeVarShort(ByteBuf buf, int toWrite) {
		int low = toWrite & 0x7FFF;
		int high = (toWrite & 0x7F8000) >> 15;
		
		if (high != 0)
			low = low | 0x8000;
		
		buf.writeShort(low);
		
		if (high != 0)
			buf.writeByte(high);
	}

	public static void writeUUID(UUID value, ByteBuf output) {
		output.writeLong(value.getMostSignificantBits());
		output.writeLong(value.getLeastSignificantBits());
	}

	public static UUID readUUID(ByteBuf input) {
		return new UUID(input.readLong(), input.readLong());
	}

	@SuppressWarnings("unchecked")
	public static void writeMetadata(ByteBuf out, MetaParam<?>[] parameters) throws IOException {
		for (MetaParam<?> parameter : parameters) {
			Objects.requireNonNull(parameter, "Metadata cannot be null!");
			
			int type = (parameter.getType() << 5 | parameter.getIndex() & 31) & 255;
			out.writeByte(type);

			switch (parameter.getType()) {
				case MetaParam.TYPE_BYTE:
					out.writeByte(((MetaParam<Byte>) parameter).getValue());
					break;

				case MetaParam.TYPE_SHORT:
					out.writeShort(((MetaParam<Short>) parameter).getValue());
					break;

				case MetaParam.TYPE_INT:
					out.writeInt(((MetaParam<Integer>) parameter).getValue());
					break;

				case MetaParam.TYPE_FLOAT:
					out.writeFloat(((MetaParam<Float>) parameter).getValue());
					break;

				case MetaParam.TYPE_STRING:
					writeString(((MetaParam<String>) parameter).getValue(), out);
					break;

				case MetaParam.TYPE_ITEM:
					ItemStack item = ((MetaParam<ItemStack>) parameter).getValue();
					writeItemstack(item, out);
					break;

				case MetaParam.TYPE_COORDINATE:
					Position coord = ((MetaParam<Position>) parameter).getValue();
					writePosIntegers(coord.intX(), coord.intY(), coord.intZ(), out);
			}
		}
		out.writeByte(127);
	}

	public static MetaParam<?>[] readMetadata(ByteBuf in) throws IOException {
		MetaParam<?>[] parameters = new MetaParam<?>[MetaParam.METADATA_SIZE];

		for (int data = in.readUnsignedByte(); data != 127; data = in.readUnsignedByte()) {
			int index = data & 0x1F;
			int type = data >> 5;

			switch (type) {
				case MetaParam.TYPE_BYTE:
					parameters[index] = new MetaParam<Byte>(index, in.readByte());
					break;

				case MetaParam.TYPE_SHORT:
					parameters[index] = new MetaParam<Short>(index, in.readShort());
					break;

				case MetaParam.TYPE_INT:
					parameters[index] = new MetaParam<Integer>(index, in.readInt());
					break;

				case MetaParam.TYPE_FLOAT:
					parameters[index] = new MetaParam<Float>(index, in.readFloat());
					break;

				case MetaParam.TYPE_STRING:
					parameters[index] = new MetaParam<String>(index, readString(in));
					break;

				case MetaParam.TYPE_ITEM:
					parameters[index] = new MetaParam<ItemStack>(index, readItemstack(in));
					break;

				case MetaParam.TYPE_COORDINATE:
					Position pos;
					pos = new Position(in.readInt(), in.readInt(), in.readInt());

					parameters[index] = new MetaParam<Position>(index, pos);

				default:
					throw new UnsupportedOperationException("Unknown metadata ID " + type);
			}
		}
		return parameters;
	}
	
	public static void writeItemstack(ItemStack item, ByteBuf out) {
		if (item == null || item.id == 0) {
			out.writeShort(-1);
			return;
		}

		out.writeShort(item.id);
		out.writeByte(item.count);
		out.writeShort(item.data);

		out.writeShort(-1); // TODO NBT data length.
		//out.writeBytes(null); Data byte array.
	}

	public static ItemStack readItemstack(ByteBuf in) {
		int id = in.readShort();

		if (id == -1)
			return null;

		int count = in.readByte();
		int damage = in.readShort();
		int dataLength = in.readShort();
		byte[] nbtData = null;

		if(dataLength > 0) {
			nbtData = new byte[dataLength];
			in.readBytes(nbtData);
		}

		return new ItemStack(id, count, damage);
	}
	
	public static void writePosIntegers(int x, int y, int z, ByteBuf out) {
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
	}
	
	public void read(ByteBuf in) throws IOException {
		throw new PacketException("Packet " + this + " is missing read method!");
	}

	public void write(ByteBuf out) throws IOException {
		throw new PacketException("Packet " + this + " is missing write method!");
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}

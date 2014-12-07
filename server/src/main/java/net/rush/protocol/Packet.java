package net.rush.protocol;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import net.rush.exceptions.PacketException;

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

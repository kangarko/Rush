package net.rush.util;

import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import net.rush.model.ItemStack;
import net.rush.model.Position;
import net.rush.util.nbt.CompoundTag;
import net.rush.util.nbt.NBTInputStream;
import net.rush.util.nbt.NBTOutputStream;
import net.rush.util.nbt.Tag;

/**
 * Contains several {@link ByteBuf}-related utility methods.

 */
public final class ByteBufUtils {

	/**
	 * The UTF-8 character set.
	 */
	private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");
	
	/**
	* The bit flag indicating a varint continues.
	*/
	public static final byte VARINT_MORE_FLAG = (byte) (1 << 7);

	/**
	 * Writes a list of parameters (e.g. mob metadata) to the buffer.
	 * @param buf The buffer.
	 * @param parameters The parameters.
	 */
	@SuppressWarnings("unchecked")
	public static void writeParameters(ByteBuf buf, Parameter<?>[] parameters) {
		for (Parameter<?> parameter : parameters) {
			if (parameter == null)
				continue;

			int type  = parameter.getType();
			int index = parameter.getIndex();

			buf.writeByte(((type & 0x07) << 5) | (index & 0x1F));

			switch (type) {
			case Parameter.TYPE_BYTE:
				buf.writeByte(((Parameter<Byte>) parameter).getValue());
				break;
			case Parameter.TYPE_SHORT:
				buf.writeShort(((Parameter<Short>) parameter).getValue());
				break;
			case Parameter.TYPE_INT:
				buf.writeInt(((Parameter<Integer>) parameter).getValue());
				break;
			case Parameter.TYPE_FLOAT:
				buf.writeFloat(((Parameter<Float>) parameter).getValue());
				break;
			case Parameter.TYPE_STRING:
				writeString(buf, ((Parameter<String>) parameter).getValue());
				break;
			case Parameter.TYPE_ITEM:
				ItemStack item = ((Parameter<ItemStack>) parameter).getValue();
				buf.writeShort(item.getId());
				buf.writeByte(item.getCount());
				buf.writeShort(item.getDamage());
				break;
			case Parameter.TYPE_COORDINATE:
				Position coord = ((Parameter<Position>) parameter).getValue();
				buf.writeInt((int) coord.x);
				buf.writeInt((int) coord.y);
				buf.writeInt((int) coord.z);
				break;
			}
		}

		buf.writeByte(0x7F);
	}

	/**
	 * Reads a list of parameters from the buffer.
	 * @param buf The buffer.
	 * @return The parameters.
	 */
	public static Parameter<?>[] readParameters(ByteBuf buf) {
		Parameter<?>[] parameters = new Parameter<?>[Parameter.METADATA_SIZE];

		int b;
		while ((b = buf.readUnsignedByte()) != 0x7F) {
			int type  = (b >> 5) & 0x07;
			int index = b & 0x1F;

			switch (type) {
			case Parameter.TYPE_BYTE:
				parameters[index] = new Parameter<Byte>(type, index, buf.readByte());
				break;
			case Parameter.TYPE_SHORT:
				parameters[index] = new Parameter<Short>(type, index, buf.readShort());
				break;
			case Parameter.TYPE_INT:
				parameters[index] = new Parameter<Integer>(type, index, buf.readInt());
				break;
			case Parameter.TYPE_FLOAT:
				parameters[index] = new Parameter<Float>(type, index, buf.readFloat());
				break;
			case Parameter.TYPE_STRING:
				parameters[index] = new Parameter<String>(type, index, readString(buf));
				break;
			case Parameter.TYPE_ITEM:
				int id = buf.readShort();
				int count = buf.readByte();
				int damage = buf.readShort();
				ItemStack item = new ItemStack(id, count, damage);
				parameters[index] = new Parameter<ItemStack>(type, index, item);
				break;
			case Parameter.TYPE_COORDINATE:
				int x = buf.readInt();
				int y = buf.readInt();
				int z = buf.readInt();
				Position coordinate = new Position(x, y, z);
				parameters[index] = new Parameter<Position>(type, index, coordinate);
				break;
			}
		}

		return parameters;
	}

	/**
	 * Writes a string to the buffer.
	 * @param buf The buffer.
	 * @param str The string.
	 * @throws IllegalArgumentException if the string is too long
	 * <em>after</em> it is encoded.
	 */
	public static void writeString(ByteBuf buf, String str) {
		int len = str.length();
		if (len >= 65536) {
			throw new IllegalArgumentException("String too long.");
		}

		buf.writeShort(len);
		for (int i = 0; i < len; i++) {
			buf.writeChar(str.charAt(i));
		}
	}

	/**
	 * Writes a UTF-8 string to the buffer.
	 * @param buf The buffer.
	 * @param str The string.
	 * @throws IllegalArgumentException if the string is too long
	 * <em>after</em> it is encoded.
	 */
	public static void writeUtf8String(ByteBuf buf, String str) {
		byte[] bytes = str.getBytes(CHARSET_UTF8);
		if (bytes.length >= 65536) {
			throw new IllegalArgumentException("Encoded UTF-8 string too long.");
		}

		buf.writeShort(bytes.length);
		buf.writeBytes(bytes);
	}

	/**
	 * Reads a string from the buffer.
	 * @param buf The buffer.
	 * @return The string.
	 */
	public static String readString(ByteBuf buf) {
		int len = buf.readUnsignedShort();

		char[] characters = new char[len];
		for (int i = 0; i < len; i++) {
			characters[i] = buf.readChar();
		}

		return new String(characters);
	}


	/**
	 * Reads a UTF-8 encoded string from the buffer.
	 * @param buf The buffer.
	 * @return The string.
	 */
	public static String readUtf8String(ByteBuf buf) {
		int len = buf.readUnsignedShort();

		byte[] bytes = new byte[len];
		buf.readBytes(bytes);

		return new String(bytes, CHARSET_UTF8);
	}
	
	/**
	 * Read a protobuf varint from the buffer.
	 * @param buf The buffer.
	 * @return The value read.
	 */
	public static int readVarInt(ByteBuf buf) {
		int ret = 0;
		short read;
		byte offset = 0;
		do {
			read = buf.readUnsignedByte();
			ret = ret | ((read & ~VARINT_MORE_FLAG) << offset);
			offset += 7;
		} while (((read >> 7) & 1) != 0);
		return ret;
	}

	/**
	 * Write a protobuf varint to the buffer.
	 * @param buf The buffer.
	 * @param num The value to write.
	 */
	public static void writeVarInt(ByteBuf buf, int num) {
		do {
			short write = (short) (num & ~VARINT_MORE_FLAG);
			num >>= 7;
		if (num != 0) {
			write |= VARINT_MORE_FLAG;
		}
		buf.writeByte(write);
		} while (num != 0);
	}
	
    public static Map<String, Tag> readCompound(ByteBuf buf) {
        int len = buf.readShort();
        if (len >= 0) {
            byte[] bytes = new byte[len];
            buf.readBytes(bytes);
            NBTInputStream str = null;
            try {
                str = new NBTInputStream(new ByteArrayInputStream(bytes));
                Tag tag = str.readTag();
                if (tag instanceof CompoundTag) {
                    return ((CompoundTag) tag).getValue();
                }
            } catch (IOException e) {
            } finally {
                if (str != null) {
                    try {
                        str.close();
                    } catch (IOException e) {}
                }
            }
        }
        return null;
    }

    public static void writeCompound(ByteBuf buf, Map<String, Tag> data) {
        if (data == null) {
            buf.writeShort(-1);
            return;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        NBTOutputStream str = null;
        try {
            str = new NBTOutputStream(out);
            str.writeTag(new CompoundTag("", data));
            str.close();
            str = null;
            buf.writeShort(out.size());
            buf.writeBytes(out.toByteArray());
        } catch (IOException e) {
        } finally {
            if (str != null) {
                try {
                    str.close();
                } catch (IOException e) {}
            }
        }

    }

    public static void writeBoolean(ByteBuf buf, boolean bool) {
    	buf.writeByte(bool ? 1 : 0);
    }
    
	public static String readStringFromDataInput(DataInput stream, int maxlength) throws IOException {
        short recvlength = stream.readShort();
        if (recvlength > maxlength)
            throw new IOException("String longer than allowed length (" + recvlength + ")!");
        if (recvlength < 0)
            throw new IOException("A string shorter than 0??? (" + recvlength + ")");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < recvlength; i++) {
            sb.append(stream.readChar());
        }

        return sb.toString();
    }

    public static void writeStringToDataOutput(DataOutput stream, String string) throws IOException {
        stream.writeShort(string.length());
        stream.writeChars(string);
    }
    
	/**
	 * Default private constructor to prevent instantiation.
	 */
	private ByteBufUtils() {}

}


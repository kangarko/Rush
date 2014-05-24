package net.rush.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import net.rush.model.ItemStack;
import net.rush.model.Position;
import net.rush.packets.misc.MetadataType;
import net.rush.packets.serialization.HashcodeAndEqualsStub;
import net.rush.util.Parameter;

public abstract class Packet extends HashcodeAndEqualsStub {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());//.append("@").append(Integer.toHexString(hashCode()));
		sb.append(String.format(" (%1$d/0x0%1$X) ", getOpcode()));
		sb.append("[").append(getToStringDescription()).append("]");
		return sb.toString();
	}

	public Class<? extends Packet> getPacketType() {
		return super.getInterface();
	}

	public static int readVarInt(ByteBuf input) {
		int out = 0;
		int bytes = 0;
		byte in;
		while (true) {
			in = input.readByte();

			out |= (in & 0x7F) << (bytes++ * 7);

			if (bytes > 32)
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
			if (value != 0) {
				part |= 0x80;
			}

			output.writeByte(part);

			if (value == 0)
				break;
		}
	}

	public static int readVarInt(DataInput input) throws IOException {
		int out = 0;
		int bytes = 0;
		byte in;
		while (true) {
			in = input.readByte();

			out |= (in & 0x7F) << (bytes++ * 7);

			if (bytes > 32) {
				throw new RuntimeException("VarInt too big");
			}

			if ((in & 0x80) != 0x80) {
				break;
			}
		}

		return out;
	}

	public static void writeVarInt(int value, DataOutput output) throws IOException {
		int part;
		while (true) {
			part = value & 0x7F;

			value >>>= 7;
			if (value != 0) {
				part |= 0x80;
			}

			output.write(part);

			if (value == 0) {
				break;
			}
		}
	}

    public String readString(DataInput datainput, int maxLength , boolean compatmode) throws IOException {
        if(compatmode){
            short short1 = datainput.readShort();
            if (short1 > maxLength) {
                throw new IOException("Received string length longer than maximum allowed (" + short1 + " > " + maxLength + ")");
            } else if (short1 < 0) {
                throw new IOException("Received string length is less than zero! Weird string!");
            } else {
                StringBuilder stringbuilder = new StringBuilder();
                for (int j = 0; j < short1; ++j) {
                    stringbuilder.append(datainput.readChar());
                }
                return stringbuilder.toString();
            }
        }
        int len = readVarInt( datainput);
        byte[] b = new byte[ len ];
        datainput.readFully( b );

        return new String( b, StandardCharsets.UTF_8 );
    }
    
    public void writeString(String string, DataOutput output , boolean compatmode) throws IOException {
        if(compatmode){
            if (string.length() > 32767)
                throw new IOException("String too big");
            else {
                output.writeShort(string.length());
                output.writeChars(string);
                return;
            }
        }
        byte[] b = string.getBytes( StandardCharsets.UTF_8 );
        writeVarInt( b.length, output );
        output.write( b );
    }
	
    public static void writeItemstack(ItemStack item, DataOutput output) throws IOException {
    	if (item == ItemStack.NULL_ITEMSTACK || item.getId() <= 0) { // FIXME less then zero check
    		output.writeShort(-1);
		} else {
			output.writeShort(item.getId());
			output.writeByte(item.getCount());
			output.writeShort(item.getDamage());
			output.writeShort(item.getDataLength());
			if (item.getDataLength() > 0) { // FIXME previous check if its enchantable // TODO is is Id or datalength?
				output.write(item.getData());
			}
		}
    }
    
    public ItemStack readItemstack(DataInput input) throws IOException {
		short id = input.readShort();
		if (id <= 0) {
			return ItemStack.NULL_ITEMSTACK;
		} else {
			byte stackSize = input.readByte();
			short dataValue = input.readShort();
			short dataLenght = input.readShort();
			byte[] metadata = new byte[0];
			if (dataLenght >= 0 && id != 0) { // FIXME previous check if its enchantable. Since MC 1.3.2 all items except 0 (empty hand) can send this.
				metadata = new byte[dataLenght];
				input.readFully(metadata);
			}
			return new ItemStack(id, stackSize, dataValue);
		}
    }
    
    @SuppressWarnings("unchecked")
	public void writeMetadata(DataOutput output, Parameter<?>[] parameters) throws IOException {
    	for (Parameter<?> parameter : parameters) {

			if (parameter == null)
				continue;

			int type = (parameter.getType() << 5 | parameter.getIndex() & 31) & 255;
			output.writeByte(type);
			
			//output.writeByte(((type & 0x07) << 5) | (parameter.getIndex() & 0x1F));
			
			switch (parameter.getType()) {
				case Parameter.TYPE_BYTE:
					output.writeByte(((Parameter<Byte>) parameter).getValue());
					break;
					
				case Parameter.TYPE_SHORT:
					output.writeShort(((Parameter<Short>) parameter).getValue());
					break;
					
				case Parameter.TYPE_INT:
					output.writeInt(((Parameter<Integer>) parameter).getValue());
					break;
					
				case Parameter.TYPE_FLOAT:
					output.writeFloat(((Parameter<Float>) parameter).getValue());
					break;
					
				case Parameter.TYPE_STRING:
					writeString(((Parameter<String>) parameter).getValue(), output, false);
					break;
					
				case Parameter.TYPE_ITEM:
					ItemStack item = ((Parameter<ItemStack>) parameter).getValue();

					if (item.getId() <= 0) {
						output.writeShort(-1);
					} else {
						output.writeShort(item.getId());
						output.writeByte(item.getCount());
						output.writeShort(item.getDamage());
						output.writeShort(-1);
						// TODO implement NBT tag writing
					}
					break;
					
				case Parameter.TYPE_COORDINATE:
					Position coord = ((Parameter<Position>) parameter).getValue();
					
					output.writeInt((int) coord.x);
					output.writeInt((int) coord.y);
					output.writeInt((int) coord.z);
			}
		}
    	output.writeByte(127);
    }
    
    public Parameter<?>[] readMetadata(DataInput input) throws IOException {
    	
    	Parameter<?>[] parameters = new Parameter<?>[Parameter.METADATA_SIZE];
    	
		for (int data = input.readUnsignedByte(); data != 127; data = input.readUnsignedByte()) {
			int index = data & 0x1F;
			int type = data >> 5;
					
			MetadataType metaType = MetadataType.fromId(type);
			
			switch (metaType) {
				case BYTE:
					parameters[index] = new Parameter<Byte>(type, index, input.readByte());
					break;
					
				case SHORT:
					parameters[index] = new Parameter<Short>(type, index, input.readShort());
					break;
					
				case INT:
					parameters[index] = new Parameter<Integer>(type, index, input.readInt());
					break;
					
				case FLOAT:
					parameters[index] = new Parameter<Float>(type, index, input.readFloat());
					break;
					
				case STRING:
					parameters[index] = new Parameter<String>(type, index, readString(input, 9999999, false));
					break;
					
				case ITEM:
					short id = input.readShort();
					if (id <= 0) {
						parameters[index] = new Parameter<ItemStack>(type, index, ItemStack.NULL_ITEMSTACK);
					} else {
						byte stackSize = input.readByte();
						short dataValue = input.readShort();
						// TODO Implement NBT tag reading
						parameters[index] = new Parameter<ItemStack>(type, index, new ItemStack(id, stackSize, dataValue));
					}
					break;
					
				case POSITION:
					int x = input.readInt();
					int y = input.readInt();
					int z = input.readInt();
					
					parameters[index] = new Parameter<Position>(type, index, new Position(x, y, z));
					
				default:
					throw new UnsupportedOperationException("Metadata-type '" + metaType + "' is not implemented!");
			}
		}
		return parameters;
    }
    
	public abstract String getToStringDescription();

	public abstract int getOpcode();

	public void read17(ByteBufInputStream input) throws IOException {
		throw new UnsupportedOperationException("PacketErr: Reading of " + this + " not possible or not implemented");
	}

	public void write17(ByteBufOutputStream output) throws IOException {
		throw new UnsupportedOperationException("PacketErr: Writing " + this + " not possible or not implemented");
	}
	
	public void read176(ByteBufInputStream input) throws IOException {
		read17(input);
	}

	public void write176(ByteBufOutputStream output) throws IOException {
		write17(output);
	}
	
	public void read18(ByteBufInputStream input) throws IOException {
		read17(input);
	}

	public void write18(ByteBufOutputStream output) throws IOException {
		write17(output);
	}
}

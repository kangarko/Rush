package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class UpdateTileEntityPacket extends Packet {
	
	public UpdateTileEntityPacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int x;
	@Serialize(type = Type.SHORT, order = 1)
	private short y;
	@Serialize(type = Type.INT, order = 2)
	private int z;
	@Serialize(type = Type.BYTE, order = 3)
	private byte action;
	@Serialize(type = Type.SHORT, order = 4)
	private short dataLength;
	@Serialize(type = Type.BYTE_ARRAY, order = 6)
	private byte[] data;

	public UpdateTileEntityPacket(int x, short y, int z, byte action, byte[] data) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.action = action;
		this.dataLength = (short) data.length;
		this.data = data;
	}

	public int getOpcode() {
		return 0x84;
	}

	public int getX() {
		return x;
	}

	public short getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public byte getAction() {
		return action;
	}

	public short getDataLength() {
		return dataLength;
	}

	public byte[] getData() {
		return data;
	}

	public String getToStringDescription() {
		return String.format("x=\"%d\",y=\"%d\",z=\"%d\",action=\"%d\",dataLenght=\"%d\",data=\"%d\"", x, y, z, action, dataLength, data);
	}
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeInt(x);
		output.writeShort(y);
		output.writeInt(z);
		output.writeByte(action);
		output.writeShort(dataLength);
		output.write(data);
	}

}

package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class UpdateSignPacket extends Packet {
	
	public UpdateSignPacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int x;
	@Serialize(type = Type.SHORT, order = 1)
	private short y;
	@Serialize(type = Type.INT, order = 2)
	private int z;
	@Serialize(type = Type.STRING, order = 3)
	private String line1;
	@Serialize(type = Type.STRING, order = 4)
	private String line2;
	@Serialize(type = Type.STRING, order = 5)
	private String line3;
	@Serialize(type = Type.STRING, order = 6)
	private String line4;

	public UpdateSignPacket(int x, short y, int z, String[] lines) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.line1 = lines[0] == null ? "" : lines[0];
		this.line2 = lines[1] == null ? "" : lines[1];
		this.line3 = lines[2] == null ? "" : lines[2];
		this.line4 = lines[3] == null ? "" : lines[3];
	}

	public int getOpcode() {
		return 0x82;
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

	public String getLine1() {
		return line1;
	}

	public String getLine2() {
		return line2;
	}

	public String getLine3() {
		return line3;
	}

	public String getLine4() {
		return line4;
	}

	public String getToStringDescription() {
		return String.format("x=\"%d\",y=\"%d\",z=\"%d\",line1=\"%s\",line2=\"%s\",line3=\"%s\",line4=\"%s\"", x, y, z, line1, line2, line3, line4);
	}
	
	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		x = input.readInt();
		y = input.readShort();
		z = input.readInt();
		line1 = readString(input, 16, false);
		line2 = readString(input, 16, false);
		line3 = readString(input, 16, false);
		line4 = readString(input, 16, false);
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeInt(x);
		output.writeShort(y);
		output.writeInt(z);
		writeString(line1, output, false);
		writeString(line2, output, false);
		writeString(line3, output, false);
		writeString(line4, output, false);
	}
}

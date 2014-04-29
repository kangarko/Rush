package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class UpdateSignPacket extends Packet {
	public UpdateSignPacket() {
		// TODO Auto-generated constructor stub
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

	public UpdateSignPacket(int x, short y, int z, String line1, String line2,
			String line3, String line4) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.line1 = line1;
		this.line2 = line2;
		this.line3 = line3;
		this.line4 = line4;
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
		return String
				.format("x=\"%d\",y=\"%d\",z=\"%d\",line1=\"%s\",line2=\"%s\",line3=\"%s\",line4=\"%s\"",
						x, y, z, line1, line2, line3, line4);
	}

	@Override
	public void read17(ByteBufInputStream input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write17(ByteBufOutputStream output) {
		// TODO Auto-generated method stub

	}
}

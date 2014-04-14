package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class UpdateTileEntityPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int x;
	@Serialize(type = Type.SHORT, order = 1)
	private final short y;
	@Serialize(type = Type.INT, order = 2)
	private final int z;
	@Serialize(type = Type.BYTE, order = 3)
	private final byte action;
	@Serialize(type = Type.INT, order = 4)
	private final int custom1;
	@Serialize(type = Type.INT, order = 5)
	private final int custom2;
	@Serialize(type = Type.INT, order = 6)
	private final int custom3;

	public UpdateTileEntityPacket(int x, short y, int z, byte action, int custom1, int custom2, int custom3) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.action = action;
		this.custom1 = custom1;
		this.custom2 = custom2;
		this.custom3 = custom3;
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

	public int getCustom1() {
		return custom1;
	}

	public int getCustom2() {
		return custom2;
	}

	public int getCustom3() {
		return custom3;
	}

	public String getToStringDescription() {
		return String.format("x=\"%d\",y=\"%d\",z=\"%d\",action=\"%d\",custom1=\"%d\",custom2=\"%d\",custom3=\"%d\"", x, y, z, action, custom1, custom2, custom3);
	}
}

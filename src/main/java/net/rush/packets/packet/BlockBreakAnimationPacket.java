package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.model.Position;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class BlockBreakAnimationPacket extends Packet {
	public BlockBreakAnimationPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.INT, order = 1)
	private int x;
	@Serialize(type = Type.INT, order = 2)
	private int y;
	@Serialize(type = Type.INT, order = 3)
	private int z;
	@Serialize(type = Type.BYTE, order = 4)
	private byte stage;

	public BlockBreakAnimationPacket(int entityId, Position pos, byte stage) {
		super();
		this.entityId = entityId;
		x = (int) pos.getX();
		y = (int) pos.getY();
		z = (int) pos.getZ();
		this.stage = stage;
	}

	public int getOpcode() {
		return 0x37;
	}

	public int getEntityId() {
		return entityId;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public byte getStage() {
		return stage;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",x=\"%d\",y=%d,z=%d,stage=%d",
				entityId, x, y, z, stage);
	}

	@Override
	public void read18(ByteBufInputStream input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write18(ByteBufOutputStream output) {
		// TODO Auto-generated method stub

	}
}

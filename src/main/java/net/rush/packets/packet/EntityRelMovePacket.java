package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityRelMovePacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte diffX;
	@Serialize(type = Type.BYTE, order = 2)
	private byte diffY;
	@Serialize(type = Type.BYTE, order = 3)
	private byte diffZ;

	public EntityRelMovePacket(int entityId, byte diffX, byte diffY, byte diffZ) {
		super();
		this.entityId = entityId;
		this.diffX = diffX;
		this.diffY = diffY;
		this.diffZ = diffZ;
	}

	public int getOpcode() {
		return 0x1F;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getDiffX() {
		return diffX;
	}

	public byte getDiffY() {
		return diffY;
	}

	public byte getDiffZ() {
		return diffZ;
	}

	public String getToStringDescription() {
		return String.format(
				"entityId=\"%d\",diffX=\"%d\",diffY=\"%d\",diffZ=\"%d\"",
				entityId, diffX, diffY, diffZ);
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

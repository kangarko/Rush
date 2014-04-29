package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityLookPacket extends Packet {
	public EntityLookPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte yaw;
	@Serialize(type = Type.BYTE, order = 2)
	private byte pitch;

	public EntityLookPacket(int entityId, byte yaw, byte pitch) {
		super();
		this.entityId = entityId;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public int getOpcode() {
		return 0x20;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getYaw() {
		return yaw;
	}

	public byte getPitch() {
		return pitch;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",yaw=\"%d\",pitch=\"%d\"",
				entityId, yaw, pitch);
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

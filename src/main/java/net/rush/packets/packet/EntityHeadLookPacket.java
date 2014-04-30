package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityHeadLookPacket extends Packet {
	public EntityHeadLookPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte headYaw;

	public EntityHeadLookPacket(int entityId, byte headYaw) {
		super();
		this.entityId = entityId;
		this.headYaw = headYaw;
	}

	public int getOpcode() {
		return 0x23;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getHeadYaw() {
		return headYaw;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",headYaw=\"%d\"", entityId,
				headYaw);
	}

	@Override
	public void read17(ByteBufInputStream input) {
		// TODO Auto-generated method stub
		System.out.println("reading entityheadlook");
	}

	@Override
	public void write17(ByteBufOutputStream output) {
		// TODO Auto-generated method stub
		System.out.println("writing entityheadlook");
	}
}

package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class RemoveEntityEffectPacket extends Packet {
	public RemoveEntityEffectPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte effectId;

	public RemoveEntityEffectPacket(int entityId, byte effectId) {
		super();
		this.entityId = entityId;
		this.effectId = effectId;
	}

	public int getOpcode() {
		return 0x2A;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getEffectId() {
		return effectId;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",effect=\"%d\"", entityId,
				effectId);
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

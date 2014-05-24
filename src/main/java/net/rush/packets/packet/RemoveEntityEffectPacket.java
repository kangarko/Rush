package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class RemoveEntityEffectPacket extends Packet {
	
	public RemoveEntityEffectPacket() {
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
		return String.format("entityId=\"%d\",effect=\"%d\"", entityId, effectId);
	}
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeInt(entityId);
		output.writeByte(effectId);
	}
}

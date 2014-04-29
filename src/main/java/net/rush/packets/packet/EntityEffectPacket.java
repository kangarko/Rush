package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityEffectPacket extends Packet {
	public EntityEffectPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte effectId;
	@Serialize(type = Type.BYTE, order = 2)
	private byte amplifier;
	@Serialize(type = Type.SHORT, order = 3)
	private short duration;

	public EntityEffectPacket(int entityId, byte effectId, byte amplifier,
			short duration) {
		super();
		this.entityId = entityId;
		this.effectId = effectId;
		this.amplifier = amplifier;
		this.duration = duration;
	}

	public int getOpcode() {
		return 0x29;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getEffectId() {
		return effectId;
	}

	public byte getAmplifier() {
		return amplifier;
	}

	public short getDuration() {
		return duration;
	}

	public String getToStringDescription() {
		return String
				.format("entityId=\"%d\",effectId=\"%d\",amplifier=\"%d\",duration=\"%d\"",
						entityId, effectId, amplifier, duration);
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

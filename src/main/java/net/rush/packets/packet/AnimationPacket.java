package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.util.enums.AnimationEnum;

public class AnimationPacket extends Packet {
	
	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte animation;

	public AnimationPacket() {
	}

	public AnimationPacket(int entityId, AnimationEnum animation) {
		this(entityId, animation.getId());
	}
	
	public AnimationPacket(int entityId, byte animation) {
		super();
		this.entityId = entityId;
		this.animation = animation;
	}

	public int getOpcode() {
		return 0x12;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getAnimation() {
		return animation;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",animation=\"%d\"", entityId, animation);
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		entityId = input.readInt();
		animation = input.readByte();
	}
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		writeVarInt(entityId, output);
		output.writeByte(animation);
	}
}

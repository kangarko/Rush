package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class AnimationPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private final byte animation;

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
}

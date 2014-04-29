package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class AnimationPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte animation;

	public AnimationPacket() {};
	
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
		return String.format("entityId=\"%d\",animation=\"%d\"", entityId,
				animation);
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

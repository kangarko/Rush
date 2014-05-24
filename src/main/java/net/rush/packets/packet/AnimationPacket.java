package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class AnimationPacket extends Packet {
	
	public static final int SWING_ARM = 1;
	public static final int DAMAGE_ANIMATION = 2;
	public static final int BED_LEAVE = 3;
	public static final int EAT_FOOD = 5;
	public static final int CRITICAL_EFFECT = 6;
	public static final int MAGIC_CRITICAL_EFFECT = 7;
	public static final int UNKNOWN = 102;
	public static final int CROUNCH = 104;
	public static final int UNCROUNCH = 105;
	
	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte animation;

	/** To prevent typos, use inbuilt types ids. */
	public AnimationPacket() {
	}
	
	/** To prevent typos, use inbuilt types ids. */
	public AnimationPacket(int entityId, int animation) {
		this(entityId, (byte)animation);
	}
	
	/** To prevent typos, use inbuilt types ids. */
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
		output.writeByte(toNewId(animation));
	}
	
	private int toNewId(int id) {
		if(id == 2)
			return 1;
		if(id == 1)
			return 0;
		return id-= 2;
	}
}

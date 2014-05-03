package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.model.Position;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SpawnObjectPacket extends Packet {
	
	public static final int BOAT = 1;
	/** Use EntityMetadataPacket to send item type and data.*/
	public static final int ITEM = 2;
	public static final int MINECART = 10;	
	/** @deprecated unused since 1.6.x  */
	public static final int STORAGE_MINECART = 11;
	/** @deprecated unused since 1.6.x  */
	public static final int POWERED_MINECART = 12;
	public static final int ACTIVATED_TNT = 50;
	public static final int ENDER_CRYSTAL = 51;
	public static final int ARROW = 61;
	public static final int SNOWBALL = 62;
	public static final int EGG = 63;
	public static final int FIRE_CHARGE = 64;
	public static final int THROWN_ENDERPARL = 65;
	public static final int WITHER_SKULL = 66;
	public static final int FALLING_BLOCK = 70;
	public static final int ITEM_FRAME = 71;
	public static final int EYE_OF_ENDER = 72;
	public static final int THROWN_POTION = 73;
	public static final int FALLING_DRAGIN_EGG = 74;
	public static final int THROWN_EXP_BOTTLE = 75;
	public static final int FISHING_BOAT = 90;
	
	/** To prevent typos, use inbuilt types ids. */
	public SpawnObjectPacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte type;
	@Serialize(type = Type.INT, order = 2)
	private int x;
	@Serialize(type = Type.INT, order = 3)
	private int y;
	@Serialize(type = Type.INT, order = 4)
	private int z;
	@Serialize(type = Type.BYTE, order = 5)
	private byte pitch;
	@Serialize(type = Type.BYTE, order = 6)
	private byte yaw;
	@Serialize(type = Type.INT, order = 7)
	private int integer;

	/** To prevent typos, use inbuilt types ids. */
	public SpawnObjectPacket(int entityId, int type, Position pos, int pitch, int yaw) {
		super();
		this.entityId = entityId;
		this.type = (byte) type;
		x = (int) pos.getPixelX();
		y = (int) pos.getPixelY();
		z = (int) pos.getPixelZ();
		this.pitch = (byte)pitch;
		this.yaw = (byte)yaw;
		integer = 0; // TODO
	}

	public int getOpcode() {
		return 0x17;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public byte getPitch() {
		return pitch;
	}

	public byte getYaw() {
		return yaw;
	}

	public int getInteger() {
		return integer;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",type=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\",fireballThrower=\"%d\"," + "fireballSpeedX=\"%d\",fireballSpeedY=\"%d\",fireballSpeedZ=\"%d\"", entityId, type, x, y, z);
	}
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		writeVarInt(entityId, output);
		output.writeByte(type);
		output.writeInt(x);
		output.writeInt(y);
		output.writeInt(z);
		output.writeByte(pitch);
		output.writeByte(yaw);
		output.writeInt(integer);
	}
}

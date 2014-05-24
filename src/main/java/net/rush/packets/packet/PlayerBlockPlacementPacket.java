package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import net.rush.model.ItemStack;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerBlockPlacementPacket extends Packet {
	
	public PlayerBlockPlacementPacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int x;
	@Serialize(type = Type.BYTE, order = 1)
	private byte y;
	@Serialize(type = Type.INT, order = 2)
	private int z;
	@Serialize(type = Type.BYTE, order = 3)
	private byte direction;
	@Serialize(type = Type.ITEM, order = 4)
	private ItemStack heldItem;
	@Serialize(type = Type.BYTE, order = 5)
	private byte cursorX;
	@Serialize(type = Type.BYTE, order = 6)
	private byte cursorY;
	@Serialize(type = Type.BYTE, order = 7)
	private byte cursorZ;

	public PlayerBlockPlacementPacket(int x, byte y, int z, byte action, ItemStack heldItem, byte cursorX, byte cursorY, byte cursorZ) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		direction = action;
		this.heldItem = heldItem;
		this.cursorX = cursorX;
		this.cursorY = cursorY;
		this.cursorZ = cursorZ;
	}

	public int getOpcode() {
		return 0x0F;
	}

	public int getX() {
		return x;
	}

	public byte getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public byte getDirection() {
		return direction;
	}

	public ItemStack getHeldItem() {
		return heldItem;
	}

	public byte getCursorX() {
		return cursorX;
	}

	public byte getCursorY() {
		return cursorY;
	}

	public byte getCursorZ() {
		return cursorZ;
	}

	public String getToStringDescription() {
		return String.format("x=\"%d\",y=\"%d\",z=\"%d\",direction=\"%d\",heldItem=\"%s\"", x, y, z, direction, heldItem);
	}
	
	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		x = input.readInt();
		y = (byte) input.readUnsignedByte();
		z = input.readInt();
		direction = input.readByte();
		heldItem = readItemstack(input);
		cursorX = input.readByte();
		cursorY = input.readByte();
		cursorZ = input.readByte();
	}
}

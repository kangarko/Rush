package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.model.ItemStack;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SetSlotPacket extends Packet {
	
	public SetSlotPacket() {
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte windowId;
	@Serialize(type = Type.SHORT, order = 1)
	private short slot;
	@Serialize(type = Type.ITEM, order = 2)
	private ItemStack item;

	public SetSlotPacket(int windowId, int slot, ItemStack item) {
		this((byte)windowId, (short)slot, item);
	}
	
	public SetSlotPacket(byte windowId, short slot, ItemStack item) {
		super();
		this.windowId = windowId;
		this.slot = slot;
		this.item = item;
	}

	public int getOpcode() {
		return 0x67;
	}

	public byte getWindowId() {
		return windowId;
	}

	public short getSlot() {
		return slot;
	}

	public ItemStack getItem() {
		return item;
	}

	public String getToStringDescription() {
		return String.format("windowId=\"%d\",slot=\"%d\",item=\"%s\"", windowId, slot, item);
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeByte(windowId);
		output.writeShort(slot);
		writeItemstack(item, output);
	}
}

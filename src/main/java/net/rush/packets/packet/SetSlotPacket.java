package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.model.ItemStack;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SetSlotPacket extends Packet {
	public SetSlotPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte windowId;
	@Serialize(type = Type.SHORT, order = 1)
	private short slot;
	@Serialize(type = Type.ITEM, order = 2)
	private ItemStack item;

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
		return String.format("windowId=\"%d\",slot=\"%d\",item=\"%s\"",
				windowId, slot, item);
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

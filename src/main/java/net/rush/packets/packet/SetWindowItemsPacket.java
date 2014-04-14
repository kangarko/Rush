package net.rush.packets.packet;

import net.rush.model.ItemStack;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SetWindowItemsPacket extends Packet {
	@Serialize(type = Type.BYTE, order = 0)
	private final byte windowId;
	@Serialize(type = Type.SHORT, order = 1)
	private final short size;
	@Serialize(type = Type.ITEM_ARRAY, order = 2, moreInfo = 1)
	private final ItemStack[] items;

	public SetWindowItemsPacket(int windowId, int size, ItemStack[] items) {
		this((byte) windowId, (short) size, items);
	}

	public SetWindowItemsPacket(byte windowId, short size, ItemStack[] items) {
		super();
		this.windowId = windowId;
		this.size = size;
		this.items = items;
	}

	public int getOpcode() {
		return 0x68;
	}

	public byte getWindowId() {
		return windowId;
	}

	public short getSize() {
		return size;
	}

	public ItemStack[] getItems() {
		return items;
	}

	public String getToStringDescription() {
		return String.format("windowId=\"%d\",size=\"%d\",items=ItemStack[%d]", windowId, size, items.length);
	}
}

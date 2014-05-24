package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.model.ItemStack;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SetWindowItemsPacket extends Packet {
	
	public SetWindowItemsPacket() {
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte windowId;
	@Serialize(type = Type.SHORT, order = 1)
	private short size;
	@Serialize(type = Type.ITEM_ARRAY, order = 2, moreInfo = 1)
	private ItemStack[] items;

	public SetWindowItemsPacket(int windowId, ItemStack[] items) {
		this((byte) windowId, (short) items.length, items);
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
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeByte(windowId);
		output.writeShort(size);
		for(ItemStack is : items)
			writeItemstack(is, output);
	}

}

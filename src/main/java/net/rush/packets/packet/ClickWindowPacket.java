package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import net.rush.model.ItemStack;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ClickWindowPacket extends Packet {
	
	public ClickWindowPacket() {
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte windowId;
	@Serialize(type = Type.SHORT, order = 1)
	private short slot;
	@Serialize(type = Type.BYTE, order = 2)
	private byte button;
	@Serialize(type = Type.SHORT, order = 3)
	private short actionId;
	@Serialize(type = Type.BYTE, order = 4)
	private byte mode;
	@Serialize(type = Type.ITEM, order = 5)
	private ItemStack clickedItem;

	public ClickWindowPacket(byte windowId, short slot, byte button, short actionId, byte mode, ItemStack clickedItem) {
		super();
		this.windowId = windowId;
		this.slot = slot;
		this.button = button;
		this.actionId = actionId;
		this.mode = mode;
		this.clickedItem = clickedItem;
	}

	public int getOpcode() {
		return 0x66;
	}

	public byte getWindowId() {
		return windowId;
	}

	public short getSlot() {
		return slot;
	}

	public byte getButton() {
		return button;
	}

	public short getActionId() {
		return actionId;
	}

	public byte getMode() {
		return mode;
	}

	public ItemStack getClickedItem() {
		return clickedItem;
	}

	public String getToStringDescription() {
		return String.format("windowId=\"%d\",slot=\"%d\",button=\"%d\",actionId=\"%d\"," + "mode=\"%b\",clickedItem=\"%s\"", windowId, slot, button, actionId, mode, clickedItem);
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		windowId = input.readByte();
		slot = input.readShort();
		button = input.readByte();
		actionId = input.readShort();
		mode = input.readByte();
		clickedItem = readItemstack(input);
	}
}

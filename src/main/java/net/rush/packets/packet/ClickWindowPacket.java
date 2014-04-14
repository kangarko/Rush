package net.rush.packets.packet;

import net.rush.model.ItemStack;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ClickWindowPacket extends Packet {
	@Serialize(type = Type.BYTE, order = 0)
	private final byte windowId;
	@Serialize(type = Type.SHORT, order = 1)
	private final short slot;
	@Serialize(type = Type.BYTE, order = 2)
	private final byte rightClick;
	@Serialize(type = Type.SHORT, order = 3)
	private final short action;
	@Serialize(type = Type.BOOL, order = 4)
	private final boolean shiftHold;
	@Serialize(type = Type.ITEM, order = 5)
	private final ItemStack clickedItem;

	public ClickWindowPacket(byte windowId, short slot, byte rightClick, short action, boolean shiftHold, ItemStack clickedItem) {
		super();
		this.windowId = windowId;
		this.slot = slot;
		this.rightClick = rightClick;
		this.action = action;
		this.shiftHold = shiftHold;
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

	public byte getRightClick() {
		return rightClick;
	}

	public short getAction() {
		return action;
	}

	public boolean getShiftHold() {
		return shiftHold;
	}

	public ItemStack getClickedItem() {
		return clickedItem;
	}

	public String getToStringDescription() {
		return String.format("windowId=\"%d\",slot=\"%d\",rightClick=\"%d\",action=\"%d\"," + "shiftHold=\"%b\",clickedItem=\"%s\"", windowId, slot, rightClick, action, shiftHold, clickedItem);
	}
}

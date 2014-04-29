package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.model.ItemStack;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ClickWindowPacket extends Packet {
	public ClickWindowPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte windowId;
	@Serialize(type = Type.SHORT, order = 1)
	private short slot;
	@Serialize(type = Type.BYTE, order = 2)
	private byte rightClick;
	@Serialize(type = Type.SHORT, order = 3)
	private short action;
	@Serialize(type = Type.BOOL, order = 4)
	private boolean shiftHold;
	@Serialize(type = Type.ITEM, order = 5)
	private ItemStack clickedItem;

	public ClickWindowPacket(byte windowId, short slot, byte rightClick,
			short action, boolean shiftHold, ItemStack clickedItem) {
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
		return String.format(
				"windowId=\"%d\",slot=\"%d\",rightClick=\"%d\",action=\"%d\","
						+ "shiftHold=\"%b\",clickedItem=\"%s\"", windowId,
				slot, rightClick, action, shiftHold, clickedItem);
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

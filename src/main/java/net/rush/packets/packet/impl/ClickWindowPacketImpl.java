package net.rush.packets.packet.impl;

import net.rush.model.ItemStack;
import net.rush.packets.packet.ClickWindowPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ClickWindowPacketImpl extends AbstractPacket implements ClickWindowPacket {
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

    public ClickWindowPacketImpl(byte windowId, short slot, byte rightClick, short action, boolean shiftHold, ItemStack clickedItem) {
        super();
        this.windowId = windowId;
        this.slot = slot;
        this.rightClick = rightClick;
        this.action = action;
        this.shiftHold = shiftHold;
        this.clickedItem = clickedItem;
    }

    @Override
    public int getOpcode() {
        return 0x66;
    }

    @Override
    public byte getWindowId() {
        return windowId;
    }

    @Override
    public short getSlot() {
        return slot;
    }

    @Override
    public byte getRightClick() {
        return rightClick;
    }

    @Override
    public short getAction() {
        return action;
    }

    @Override
    public boolean getShiftHold() {
        return shiftHold;
    }

    @Override
    public ItemStack getClickedItem() {
        return clickedItem;
    }

    @Override
    public String getToStringDescription() {
        return String.format("windowId=\"%d\",slot=\"%d\",rightClick=\"%d\",action=\"%d\","
                + "shiftHold=\"%b\",clickedItem=\"%s\"", windowId, slot, rightClick, action, shiftHold, clickedItem);
    }
}

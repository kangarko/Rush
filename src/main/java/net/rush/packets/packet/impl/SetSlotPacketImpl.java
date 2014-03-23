package net.rush.packets.packet.impl;

import net.rush.packets.misc.ItemStack;
import net.rush.packets.packet.SetSlotPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SetSlotPacketImpl extends AbstractPacket implements SetSlotPacket {
    @Serialize(type = Type.BYTE, order = 0)
    private final byte windowId;
    @Serialize(type = Type.SHORT, order = 1)
    private final short slot;
    @Serialize(type = Type.ITEMSTACK, order = 2)
    private final ItemStack item;

    public SetSlotPacketImpl(byte windowId, short slot, ItemStack item) {
        super();
        this.windowId = windowId;
        this.slot = slot;
        this.item = item;
    }

    @Override
    public int getOpcode() {
        return 0x67;
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
    public ItemStack getItem() {
        return item;
    }

    @Override
    public String getToStringDescription() {
        return String.format("windowId=\"%d\",slot=\"%d\",item=\"%s\"", windowId, slot, item);
    }
}

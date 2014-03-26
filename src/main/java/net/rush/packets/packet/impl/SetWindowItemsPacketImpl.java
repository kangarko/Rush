package net.rush.packets.packet.impl;

import net.rush.model.Item;
import net.rush.packets.packet.SetWindowItemsPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SetWindowItemsPacketImpl extends AbstractPacket implements SetWindowItemsPacket {
    @Serialize(type = Type.BYTE, order = 0)
    private final byte windowId;
    @Serialize(type = Type.SHORT, order = 1)
    private final short size;
    @Serialize(type = Type.ITEM_ARRAY, order = 2, moreInfo = 1)
    private final Item[] items;

    public SetWindowItemsPacketImpl(int windowId, int size, Item[] items) {
        this((byte)windowId, (short)size, items);
    }
    
    public SetWindowItemsPacketImpl(byte windowId, short size, Item[] items) {
        super();
        this.windowId = windowId;
        this.size = size;
        this.items = items;
    }

    @Override
    public int getOpcode() {
        return 0x68;
    }

    @Override
    public byte getWindowId() {
        return windowId;
    }

    @Override
    public short getSize() {
        return size;
    }

    @Override
    public Item[] getItems() {
        return items;
    }

    @Override
    public String getToStringDescription() {
        return String.format("windowId=\"%d\",size=\"%d\",items=ItemStack[%d]",
                windowId, size, items.length);
    }
}

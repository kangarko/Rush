package net.rush.packets.packet.impl;

import net.rush.model.Item;
import net.rush.packets.packet.CreativeInventoryActionPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class CreativeInventoryActionPacketImpl extends AbstractPacket implements CreativeInventoryActionPacket {
    @Serialize(type = Type.SHORT, order = 0)
    private final short slot;
    @Serialize(type = Type.ITEM, order = 1)
    private final Item item;

    public CreativeInventoryActionPacketImpl(short slot, Item item) {
        super();
        this.slot = slot;
        this.item = item;
    }

    @Override
    public int getOpcode() {
        return 0x6B;
    }

    @Override
    public short getSlot() {
        return slot;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public String getToStringDescription() {
        return String.format("slot=\"%d\",item=\"%s\"", slot, item);
    }
}

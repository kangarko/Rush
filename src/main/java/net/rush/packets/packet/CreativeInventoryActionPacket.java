package net.rush.packets.packet;

import net.rush.model.Item;
import net.rush.packets.Packet;

public interface CreativeInventoryActionPacket extends Packet {
    short getSlot();
    Item getItem();
}

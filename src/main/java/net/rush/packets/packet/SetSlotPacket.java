package net.rush.packets.packet;

import net.rush.model.Item;
import net.rush.packets.Packet;

public interface SetSlotPacket extends Packet {
    byte getWindowId();
    short getSlot();
    Item getItem();
}

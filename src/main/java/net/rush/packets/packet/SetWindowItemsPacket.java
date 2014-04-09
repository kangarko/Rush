package net.rush.packets.packet;

import net.rush.model.ItemStack;
import net.rush.packets.Packet;

public interface SetWindowItemsPacket extends Packet {
    byte getWindowId();
    short getSize();
    ItemStack[] getItems();
}

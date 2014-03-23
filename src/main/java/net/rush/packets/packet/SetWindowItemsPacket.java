package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.misc.ItemStack;

public interface SetWindowItemsPacket extends Packet {
    byte getWindowId();
    short getSize();
    ItemStack[] getItems();
}

package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.misc.ItemStack;

public interface SetSlotPacket extends Packet {
    byte getWindowId();
    short getSlot();
    ItemStack getItem();
}

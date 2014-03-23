package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.misc.ItemStack;

public interface CreativeInventoryActionPacket extends Packet {
    short getSlot();
    ItemStack getItem();
}

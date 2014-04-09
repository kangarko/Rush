package net.rush.packets.packet;

import net.rush.model.ItemStack;
import net.rush.packets.Packet;

public interface CreativeInventoryActionPacket extends Packet {
    short getSlot();
    ItemStack getItem();
}

package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface EnchantItemPacket extends Packet {
    byte getWindowId();
    byte getEnchantment();
}

package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.misc.ItemStack;

public interface PlayerBlockPlacementPacket extends Packet {
    int getX();
    byte getY();
    int getZ();
    byte getDirection();
    ItemStack getHeldItem();
    byte getCursorX();
    byte getCursorY();
    byte getCursorZ();
}

package net.rush.packets.packet;

import net.rush.model.ItemStack;
import net.rush.packets.Packet;

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

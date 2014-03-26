package net.rush.packets.packet;

import net.rush.model.Item;
import net.rush.packets.Packet;

public interface PlayerBlockPlacementPacket extends Packet {
    int getX();
    byte getY();
    int getZ();
    byte getDirection();
    Item getHeldItem();
    byte getCursorX();
    byte getCursorY();
    byte getCursorZ();
}

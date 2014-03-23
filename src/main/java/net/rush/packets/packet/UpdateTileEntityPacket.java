package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface UpdateTileEntityPacket extends Packet {
    int getX();
    short getY();
    int getZ();
    byte getAction();
    int getCustom1();
    int getCustom2();
    int getCustom3();
}

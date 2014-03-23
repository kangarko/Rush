package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface UpdateSignPacket extends Packet {
    int getX();
    short getY();
    int getZ();
    String getLine1();
    String getLine2();
    String getLine3();
    String getLine4();
}

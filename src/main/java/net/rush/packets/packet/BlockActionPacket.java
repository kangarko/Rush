package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface BlockActionPacket extends Packet {
    int getX();
    short getY();
    int getZ();
    byte getByte1();
    byte getByte2();
    short getBlockId();
}

package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface UseBedPacket extends Packet {
    int getEntityId();
    byte getUnknown_byte_0();
    // coords seem to be the bed-headboard
    int getX();
    byte getY();
    int getZ();
}

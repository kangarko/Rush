package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface MultiBlockChangePacket extends Packet {
    int getChunkX();
    int getChunkZ();
    short getRecordCount();
    int getDataSize();
    byte[] getData();
}

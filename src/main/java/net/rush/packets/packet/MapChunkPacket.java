package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface MapChunkPacket extends Packet {
    int getX();

    int getZ();

    boolean getGroundUpContinuous();

    int getPrimaryBitMap();

    int getAddBitMap();

    int getCompressedSize();

    byte[] getCompressedChunkData();
}

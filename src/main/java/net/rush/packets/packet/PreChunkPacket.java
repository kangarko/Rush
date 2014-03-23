package net.rush.packets.packet;

import net.rush.packets.Packet;

// also called "MapChunkBulk". However, I prefer the name "PreChunk" :P
public interface PreChunkPacket extends Packet {
	
	short getChunkCount();
	
	int getDataLength();
	
	boolean getSkyLight();
	
	byte[] getData();
	
    int getX();

    int getZ();

    int getPrimaryBitMap();
    
    int getAddBitMap();
}

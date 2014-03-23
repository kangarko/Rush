package net.rush.packets.packet.impl;

import net.rush.packets.packet.PreChunkPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PreChunkPacketImpl extends AbstractPacket implements PreChunkPacket {
    @Serialize(type = Type.SHORT, order = 0)
    private final short chunkCount;
    @Serialize(type = Type.INT, order = 1)
    private final int dataLength;
    @Serialize(type = Type.BOOL, order = 2)
    private final boolean skyLight;
    @Serialize(type = Type.BYTE_ARRAY, order = 3)
    private final byte[] data;
    // Meta information
    @Serialize(type = Type.INT, order = 4)
    private final int x;
    @Serialize(type = Type.INT, order = 5)
    private final int z;
    @Serialize(type = Type.UNSIGNED_SHORT, order = 6)
    private final int primaryBitMap;
    @Serialize(type = Type.UNSIGNED_SHORT, order = 7)
    private final int addBitMap;

    public PreChunkPacketImpl(short chunkCount, int dataLength, boolean skyLight, byte[] data,
    		int x, int z, int primaryBitMap, int addBitMap) {
        super();
        this.chunkCount = chunkCount;
        this.dataLength = dataLength;
        this.skyLight = skyLight;
        this.data = data;
        this.x = x;
        this.z = z;
        this.primaryBitMap = primaryBitMap;
        this.addBitMap = addBitMap;
    }

    @Override
    public int getOpcode() {
        return 0x32;
    }

    @Override
    public short getChunkCount() {
        return chunkCount;
    }

    @Override
    public int getDataLength() {
        return dataLength;
    }
    
    @Override
    public boolean getSkyLight() {
        return skyLight;
    }
    
    @Override
    public byte[] getData() {
        return data;
    }
    
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getZ() {
        return z;
    }
    
    @Override
    public int getPrimaryBitMap() {
        return primaryBitMap;
    }

    @Override
    public int getAddBitMap() {
        return addBitMap;
    }

    @Override
    public String getToStringDescription() {
        return String.format("x=\"%a,%b,%c,x=%d,z=%e,%f,%g", chunkCount, dataLength, data, x, z, primaryBitMap, addBitMap);
    }
}

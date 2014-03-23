package net.rush.packets.packet.impl;

import net.rush.packets.packet.MapChunkPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class MapChunkPacketImpl extends AbstractPacket implements MapChunkPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int x;
    @Serialize(type = Type.INT, order = 1)
    private final int z;
    @Serialize(type = Type.BOOL, order = 2)
    private final boolean groundUpContinuous;
    @Serialize(type = Type.UNSIGNED_SHORT, order = 3)
    private final int primaryBitMap;
    @Serialize(type = Type.UNSIGNED_SHORT, order = 4)
    private final int addBitMap;
    @Serialize(type = Type.INT, order = 5)
    private final int compressedSize;
    @Serialize(type = Type.BYTE_ARRAY, order = 6, moreInfo = 5)
    private final byte[] compressedChunkData;

    public MapChunkPacketImpl(int x, int z, boolean groundUpContinuous, int primaryBitMap, int addBitMap, byte[] chunkData) {
        this(x, z, groundUpContinuous, primaryBitMap, addBitMap, chunkData.length, chunkData);
    }

    public MapChunkPacketImpl(int x, int z, boolean groundUpContinuous, int primaryBitMap, int addBitMap, int compressedSize, byte[] chunkData) {
        super();
        this.x = x;
        this.z = z;
        this.groundUpContinuous = groundUpContinuous;
        this.primaryBitMap = primaryBitMap;
        this.addBitMap = addBitMap;
        this.compressedSize = compressedSize;
        this.compressedChunkData = chunkData;
    }

    @Override
    public int getOpcode() {
        return 0x33;
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
    public boolean getGroundUpContinuous() {
        return groundUpContinuous;
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
    public int getCompressedSize() {
        return compressedSize;
    }

    @Override
    public byte[] getCompressedChunkData() {
        return compressedChunkData;
    }

    @Override
    public String getToStringDescription() {
        return String.format(
                "x=\"%d\",z=\"%d\",groundUpContinuous=\"%b\",primaryBitMap=\"%d\",addBitMap=\"%d\",compressedSize=\"%d\",chunkData=byte[%d]",
                        x, z, groundUpContinuous, primaryBitMap, addBitMap, compressedSize, compressedChunkData.length);
    }
}

package net.rush.packets.packet.impl;

import net.rush.packets.packet.BlockChangePacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class BlockChangePacketImpl extends AbstractPacket implements BlockChangePacket {
    @Serialize(type = Type.INT, order = 0)
    private final int x;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte y;
    @Serialize(type = Type.INT, order = 2)
    private final int z;
    @Serialize(type = Type.SHORT, order = 3)
    private final short blockType;
    @Serialize(type = Type.BYTE, order = 4)
    private final byte blockMetadata;

    public BlockChangePacketImpl(int x, byte y, int z, short blockType, byte blockMetadata) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockType = blockType;
        this.blockMetadata = blockMetadata;
    }

    @Override
    public int getOpcode() {
        return 0x35;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public byte getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public short getBlockType() {
        return blockType;
    }

    @Override
    public byte getBlockMetadata() {
        return blockMetadata;
    }

    @Override
    public String getToStringDescription() {
        return String.format("x=\"%d\",y=\"%d\",z=\"%d\",blockType=\"%d\",blockMetadata=\"%d\"",
                x, y, z, blockType, blockMetadata);
    }
}

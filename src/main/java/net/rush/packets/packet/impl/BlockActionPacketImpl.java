package net.rush.packets.packet.impl;

import net.rush.packets.packet.BlockActionPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class BlockActionPacketImpl extends AbstractPacket implements BlockActionPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int x;
    @Serialize(type = Type.SHORT, order = 1)
    private final short y;
    @Serialize(type = Type.INT, order = 2)
    private final int z;
    @Serialize(type = Type.BYTE, order = 3)
    private final byte byte1;
    @Serialize(type = Type.BYTE, order = 4)
    private final byte byte2;
    @Serialize(type = Type.SHORT, order = 4)
    private final short blockId;

    public BlockActionPacketImpl(int x, short y, int z, byte byte1, byte byte2, short blockId) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
        this.byte1 = byte1;
        this.byte2 = byte2;
        this.blockId = blockId;
    }

    @Override
    public int getOpcode() {
        return 0x36;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public short getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public byte getByte1() {
        return byte1;
    }

    @Override
    public byte getByte2() {
        return byte2;
    }
    
    @Override
    public short getBlockId() {
        return blockId;
    }

    @Override
    public String getToStringDescription() {
        return String.format("x=\"%d\",y=\"%d\",z=\"%d\",byte1=\"%d\",byte2=\"%d\", blockId=\"%d\"", x, y, z, byte1, byte2, blockId);
    }
}

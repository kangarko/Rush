package net.rush.packets.packet.impl;

import net.rush.packets.packet.DestroyEntityPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class DestroyEntityPacketImpl extends AbstractPacket implements DestroyEntityPacket {
    @Serialize(type = Type.BYTE, order = 0)
    private final byte entityCount;
    @Serialize(type = Type.INT_ARRAY, order = 1)
    private final int[] entityIDs;

    public DestroyEntityPacketImpl(int[] entityIDs) {
        super();
        this.entityCount = (byte) entityIDs.length;
        this.entityIDs = entityIDs;
    }

    @Override
    public int getOpcode() {
        return 0x1D;
    }
    
    @Override
    public byte getEntityCount() {
        return entityCount;
    }

    @Override
    public int[] getEntityIDs() {
        return entityIDs;
    }

    @Override
    public String getToStringDescription() {
        return String.format("entityCount=\"%d\", entityIDs=\"%c\"", entityCount, entityIDs);
    }
}

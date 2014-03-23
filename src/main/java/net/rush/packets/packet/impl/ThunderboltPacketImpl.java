package net.rush.packets.packet.impl;

import net.rush.packets.packet.ThunderboltPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ThunderboltPacketImpl extends AbstractPacket implements ThunderboltPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte unknown_byte_0;
    @Serialize(type = Type.INT, order = 2)
    private final int x;
    @Serialize(type = Type.INT, order = 3)
    private final int y;
    @Serialize(type = Type.INT, order = 4)
    private final int z;

    public ThunderboltPacketImpl(int entityId, byte unknown_byte_0, int x, int y, int z) {
        super();
        this.entityId = entityId;
        this.unknown_byte_0 = unknown_byte_0;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public int getOpcode() {
        return 0x47;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public byte getUnknown_byte_0() {
        return unknown_byte_0;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",unknown_byte_0=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\"",
                entityId, unknown_byte_0, x, y, z);
    }
}

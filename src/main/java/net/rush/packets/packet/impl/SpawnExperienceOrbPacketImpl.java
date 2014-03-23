package net.rush.packets.packet.impl;

import net.rush.packets.packet.SpawnExperienceOrbPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SpawnExperienceOrbPacketImpl extends AbstractPacket implements
        SpawnExperienceOrbPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.INT, order = 1)
    private final int x;
    @Serialize(type = Type.INT, order = 2)
    private final int y;
    @Serialize(type = Type.INT, order = 3)
    private final int z;
    @Serialize(type = Type.SHORT, order = 4)
    private final short count;

    public SpawnExperienceOrbPacketImpl(int entityId, int x, int y, int z, short count) {
        super();
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.count = count;
    }

    @Override
    public int getOpcode() {
        return 0x1A;
    }

    @Override
    public int getEntityId() {
        return entityId;
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
    public short getCount() {
        return count;
    }

    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\",count=\"%d\"",
                entityId, x, y, z, count);
    }
}

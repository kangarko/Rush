package net.rush.packets.packet.impl;

import net.rush.packets.packet.EntityVelocityPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityVelocityPacketImpl extends AbstractPacket implements EntityVelocityPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.SHORT, order = 1)
    private final short velocityX;
    @Serialize(type = Type.SHORT, order = 2)
    private final short velocityY;
    @Serialize(type = Type.SHORT, order = 3)
    private final short velocityZ;

    public EntityVelocityPacketImpl(int entityId, short velocityX, short velocityY, short velocityZ) {
        super();
        this.entityId = entityId;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
    }

    @Override
    public int getOpcode() {
        return 0x1C;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public short getVelocityX() {
        return velocityX;
    }

    @Override
    public short getVelocityY() {
        return velocityX;
    }

    @Override
    public short getVelocityZ() {
        return velocityX;
    }

    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",velocityX=\"%d\",velocityY=\"%d\",velocityZ=\"%d\"",
                entityId, velocityX, velocityY, velocityZ);
    }
}

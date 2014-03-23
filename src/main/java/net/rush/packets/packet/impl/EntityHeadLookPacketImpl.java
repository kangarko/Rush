package net.rush.packets.packet.impl;

import net.rush.packets.packet.EntityHeadLookPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityHeadLookPacketImpl extends AbstractPacket implements EntityHeadLookPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte headYaw;

    public EntityHeadLookPacketImpl(int entityId, byte headYaw) {
        super();
        this.entityId = entityId;
        this.headYaw = headYaw;
    }

    @Override
    public int getOpcode() {
        return 0x23;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public byte getHeadYaw() {
        return headYaw;
    }

    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",headYaw=\"%d\"", entityId, headYaw);
    }
}

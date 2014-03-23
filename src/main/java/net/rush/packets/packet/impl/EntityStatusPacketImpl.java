package net.rush.packets.packet.impl;

import net.rush.packets.packet.EntityStatusPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityStatusPacketImpl extends AbstractPacket implements EntityStatusPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte status;

    public EntityStatusPacketImpl(int entityId, byte status) {
        super();
        this.entityId = entityId;
        this.status = status;
    }

    @Override
    public int getOpcode() {
        return 0x26;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public byte getStatus() {
        return status;
    }

    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",status=\"%d\"", entityId, status);
    }
}

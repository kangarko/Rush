package net.rush.packets.packet.impl;

import net.rush.packets.packet.EntityExistsPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityExistsPacketImpl extends AbstractPacket implements EntityExistsPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;

    public EntityExistsPacketImpl(int entityId) {
        super();
        this.entityId = entityId;
    }

    @Override
    public int getOpcode() {
        return 0x1E;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\"", entityId);
    }
}

package net.rush.packets.packet.impl;

import net.rush.packets.packet.EntityMetadataPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.util.Parameter;

public class EntityMetadataPacketImpl extends AbstractPacket implements EntityMetadataPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.ENTITY_METADATA, order = 1)
    private final Parameter<?>[] metadata;

    public EntityMetadataPacketImpl(int entityId, Parameter<?>[] metadata) {
        super();
        this.entityId = entityId;
        this.metadata = metadata;
    }

    @Override
    public int getOpcode() {
        return 0x28;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public Parameter<?>[] getMetadata() {
        return metadata;
    }

    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",metadata=\"%s\"", entityId, metadata);
    }
}

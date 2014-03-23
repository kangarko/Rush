package net.rush.packets.packet.impl;

import net.rush.packets.packet.EntityLookPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityLookPacketImpl extends AbstractPacket implements EntityLookPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte yaw;
    @Serialize(type = Type.BYTE, order = 2)
    private final byte pitch;

    public EntityLookPacketImpl(int entityId, byte yaw, byte pitch) {
        super();
        this.entityId = entityId;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public int getOpcode() {
        return 0x20;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public byte getYaw() {
        return yaw;
    }

    @Override
    public byte getPitch() {
        return pitch;
    }

    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",yaw=\"%d\",pitch=\"%d\"", entityId, yaw, pitch);
    }
}

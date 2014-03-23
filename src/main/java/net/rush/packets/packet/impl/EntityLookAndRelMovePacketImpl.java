package net.rush.packets.packet.impl;

import net.rush.packets.packet.EntityLookAndRelMovePacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityLookAndRelMovePacketImpl extends AbstractPacket implements EntityLookAndRelMovePacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte diffX;
    @Serialize(type = Type.BYTE, order = 2)
    private final byte diffY;
    @Serialize(type = Type.BYTE, order = 3)
    private final byte diffZ;
    @Serialize(type = Type.BYTE, order = 4)
    private final byte yaw;
    @Serialize(type = Type.BYTE, order = 5)
    private final byte pitch;

    public EntityLookAndRelMovePacketImpl(int entityId, byte diffX, byte diffY, byte diffZ, byte yaw, byte pitch) {
        super();
        this.entityId = entityId;
        this.diffX = diffX;
        this.diffY = diffY;
        this.diffZ = diffZ;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public int getOpcode() {
        return 0x21;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public byte getDiffX() {
        return diffX;
    }

    @Override
    public byte getDiffY() {
        return diffY;
    }

    @Override
    public byte getDiffZ() {
        return diffZ;
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
        return String.format(
                "entityId=\"%d\",diffX=\"%d\",diffY=\"%d\",diffZ=\"%d\",yaw=\"%d\",pitch=\"%d\"",
                entityId, diffX, diffY, diffZ, yaw, pitch);
    }
}

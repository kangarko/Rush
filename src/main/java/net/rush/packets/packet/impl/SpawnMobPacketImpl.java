package net.rush.packets.packet.impl;

import net.rush.model.Position;
import net.rush.packets.packet.SpawnMobPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.util.Parameter;

public class SpawnMobPacketImpl extends AbstractPacket implements SpawnMobPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte entityType;
    @Serialize(type = Type.INT, order = 2)
    private final int x;
    @Serialize(type = Type.INT, order = 3)
    private final int y;
    @Serialize(type = Type.INT, order = 4)
    private final int z;
    @Serialize(type = Type.BYTE, order = 5)
    private final byte yaw;
    @Serialize(type = Type.BYTE, order = 6)
    private final byte pitch;
    @Serialize(type = Type.BYTE, order = 7)
    private final byte headYaw;
    @Serialize(type = Type.SHORT, order = 8)
    private final short velocityX;
    @Serialize(type = Type.SHORT, order = 9)
    private final short velocityY;
    @Serialize(type = Type.SHORT, order = 10)
    private final short velocityZ;
    @Serialize(type = Type.ENTITY_METADATA, order = 11)
    private final Parameter<?>[] metadata;

    public SpawnMobPacketImpl(int entityId, byte entityType, Position pos,
            byte yaw, byte pitch, byte headYaw, Position velocity, Parameter<?>[] metadata) {
        super();
        this.entityId = entityId;
        this.entityType = entityType;
        this.x = (int)pos.getX();
        this.y = (int)pos.getY();
        this.z = (int)pos.getZ();
        this.yaw = yaw;
        this.pitch = pitch;
        this.headYaw = headYaw;
        this.velocityX = (short)velocity.getX();
        this.velocityY = (short)velocity.getY();
        this.velocityZ = (short)velocity.getZ();
        this.metadata = metadata;
    }

    @Override
    public int getOpcode() {
        return 0x18;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public byte getEntityType() {
        return entityType;
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
    public byte getYaw() {
        return yaw;
    }

    @Override
    public byte getPitch() {
        return pitch;
    }

    @Override
    public byte getHeadYaw() {
        return headYaw;
    }

    @Override
    public short getVelocityX() {
        return velocityX;
    }
    
    @Override
    public short getVelocityY() {
        return velocityY;
    }
    
    @Override
    public short getVelocityZ() {
        return velocityZ;
    }
    
    @Override
    public Parameter<?>[] getMetadata() {
        return metadata;
    }

    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",entityType=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\","
                + "yaw=\"%d\",pitch=\"%d\",headYaw=\"%d\",metadata=\"%s\"",
                entityId, entityType, x, y, z, yaw, pitch, headYaw, metadata);
    }
}

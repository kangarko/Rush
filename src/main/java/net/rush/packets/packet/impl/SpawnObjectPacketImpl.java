package net.rush.packets.packet.impl;

import net.rush.model.Position;
import net.rush.packets.packet.SpawnObjectPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SpawnObjectPacketImpl extends AbstractPacket implements SpawnObjectPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte type;
    @Serialize(type = Type.INT, order = 2)
    private final int x;
    @Serialize(type = Type.INT, order = 3)
    private final int y;
    @Serialize(type = Type.INT, order = 4)
    private final int z;
    @Serialize(type = Type.BYTE, order = 5)
    private final byte pitch;
    @Serialize(type = Type.BYTE, order = 6)
    private final byte yaw;
    @Serialize(type = Type.CONDITIONAL_SHORT, order = 7, moreInfo = 5)
    private final short speedX;
    @Serialize(type = Type.CONDITIONAL_SHORT, order = 8, moreInfo = 5)
    private final short speedY;
    @Serialize(type = Type.CONDITIONAL_SHORT, order = 9, moreInfo = 5)
    private final short speedZ;

    public SpawnObjectPacketImpl(int entityId, byte type, Position pos, byte pitch, byte yaw, short speedX, short speedY, short speedZ) {
        super();
        this.entityId = entityId;
        this.type = type;
        this.x = (int)pos.getX();
        this.y = (int)pos.getY();
        this.z = (int)pos.getZ();
        this.pitch = pitch;
        this.yaw = yaw;
        this.speedX = speedX;
        this.speedY = speedY;
        this.speedZ = speedZ;
    }

    @Override
    public int getOpcode() {
        return 0x17;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public byte getType() {
        return type;
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
    public byte getPitch() {
        return pitch;
    }
    
    @Override
    public byte getYaw() {
        return yaw;
    }
    
    @Override
    public short getSpeedX() {
        return speedX;
    }

    @Override
    public short getSpeedY() {
        return speedY;
    }

    @Override
    public short getSpeedZ() {
        return speedZ;
    }

    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",type=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\",fireballThrower=\"%d\","
                        + "fireballSpeedX=\"%d\",fireballSpeedY=\"%d\",fireballSpeedZ=\"%d\"",
                        entityId, type, x, y, z, speedX, speedY, speedZ);
    }
}

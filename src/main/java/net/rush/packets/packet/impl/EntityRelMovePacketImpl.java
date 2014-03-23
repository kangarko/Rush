package net.rush.packets.packet.impl;

import net.rush.packets.packet.EntityRelMovePacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityRelMovePacketImpl extends AbstractPacket implements EntityRelMovePacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte diffX;
    @Serialize(type = Type.BYTE, order = 2)
    private final byte diffY;
    @Serialize(type = Type.BYTE, order = 3)
    private final byte diffZ;

    public EntityRelMovePacketImpl(int entityId, byte diffX, byte diffY, byte diffZ) {
        super();
        this.entityId = entityId;
        this.diffX = diffX;
        this.diffY = diffY;
        this.diffZ = diffZ;
    }

    @Override
    public int getOpcode() {
        return 0x1F;
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
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",diffX=\"%d\",diffY=\"%d\",diffZ=\"%d\"", entityId,
                diffX, diffY, diffZ);
    }
}

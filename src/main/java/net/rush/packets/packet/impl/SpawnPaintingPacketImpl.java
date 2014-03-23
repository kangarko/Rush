package net.rush.packets.packet.impl;

import net.rush.packets.packet.SpawnPaintingPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SpawnPaintingPacketImpl extends AbstractPacket implements SpawnPaintingPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.STRING, order = 1)
    private final String title;
    @Serialize(type = Type.INT, order = 2)
    private final int x;
    @Serialize(type = Type.INT, order = 3)
    private final int y;
    @Serialize(type = Type.INT, order = 4)
    private final int z;
    @Serialize(type = Type.INT, order = 5)
    private final int direction;

    public SpawnPaintingPacketImpl(int entityId, String title, int x, int y, int z, int direction) {
        super();
        this.entityId = entityId;
        this.title = title;
        this.x = x;
        this.y = y;
        this.z = z;
        this.direction = direction;
    }

    @Override
    public int getOpcode() {
        return 0x19;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public String getTitle() {
        return title;
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
    public int getDirection() {
        return direction;
    }

    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",title=\"%s\",x=\"%d\",y=\"%d\",z=\"%d\",direction=\"%d\"",
                entityId, title, x, y, z, direction);
    }
}

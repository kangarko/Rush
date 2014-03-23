package net.rush.packets.packet.impl;

import net.rush.model.Position;
import net.rush.packets.packet.SpawnPositionPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SpawnPositionPacketImpl extends AbstractPacket implements SpawnPositionPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int x;
    @Serialize(type = Type.INT, order = 1)
    private final int y;
    @Serialize(type = Type.INT, order = 2)
    private final int z;

    public SpawnPositionPacketImpl(Position pos) {
        super();
        this.x = (int)pos.getX();
        this.y = (int)pos.getY();
        this.z = (int)pos.getZ();
    }

    @Override
    public int getOpcode() {
        return 0x06;
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
    public String getToStringDescription() {
        return String.format("x=\"%d\",y=\"%d\",z=\"%d\"", x, y, z);
    }
}

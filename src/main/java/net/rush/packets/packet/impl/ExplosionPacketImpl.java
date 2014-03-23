package net.rush.packets.packet.impl;

import java.util.Set;

import net.rush.model.Coordinate;
import net.rush.packets.packet.ExplosionPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ExplosionPacketImpl extends AbstractPacket implements ExplosionPacket {
    @Serialize(type = Type.DOUBLE, order = 0)
    private final double x;
    @Serialize(type = Type.DOUBLE, order = 1)
    private final double y;
    @Serialize(type = Type.DOUBLE, order = 2)
    private final double z;
    @Serialize(type = Type.FLOAT, order = 3)
    private final float size;
    @Serialize(type = Type.BLOCKCOORD_COLLECTION, order = 4)
    private final Set<Coordinate> destroyedBlocks;

    public ExplosionPacketImpl(double x, double y, double z, float size, Set<Coordinate> destroyedBlocks) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.destroyedBlocks = destroyedBlocks;
    }

    @Override
    public int getOpcode() {
        return 0x3C;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public float getSize() {
        return size;
    }

    @Override
    public Set<Coordinate> getDestroyedBlocks() {
        return destroyedBlocks;
    }

    @Override
    public String getToStringDescription() {
        return String.format("x=\"%d\",y=\"%d\",z=\"%d\",size=\"%d\",destroyedBlocks=\"%s\"",
                x, y, z, size, destroyedBlocks);
    }
}

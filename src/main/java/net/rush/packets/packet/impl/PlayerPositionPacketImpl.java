package net.rush.packets.packet.impl;

import net.rush.packets.packet.PlayerPositionPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerPositionPacketImpl extends AbstractPacket implements PlayerPositionPacket {
    @Serialize(type = Type.DOUBLE, order = 0)
    private final double x;
    @Serialize(type = Type.DOUBLE, order = 1)
    private final double y;
    @Serialize(type = Type.DOUBLE, order = 2)
    private final double stance;
    @Serialize(type = Type.DOUBLE, order = 3)
    private final double z;
    @Serialize(type = Type.BOOL, order = 4)
    private final boolean onGround;

    public PlayerPositionPacketImpl(double x, double y, double stance, double z, boolean onGround) {
        super();
        this.x = x;
        this.y = y;
        this.stance = stance;
        this.z = z;
        this.onGround = onGround;
    }

    @Override
    public int getOpcode() {
        return 0x0B;
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
    public double getStance() {
        return stance;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public boolean getOnGround() {
        return onGround;
    }

    @Override
    public String getToStringDescription() {
        return String.format("x=\"%f\",y=\"%f\",stance=\"%f\",z=\"%f\",onGround=\"%b\"", x, y,
                stance, z, onGround);
    }
}

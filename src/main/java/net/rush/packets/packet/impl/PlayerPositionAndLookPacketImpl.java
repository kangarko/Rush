package net.rush.packets.packet.impl;

import net.rush.packets.packet.PlayerPositionAndLookPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerPositionAndLookPacketImpl extends AbstractPacket implements PlayerPositionAndLookPacket {
    @Serialize(type = Type.DOUBLE, order = 0)
    private final double x;
    @Serialize(type = Type.DOUBLE, order = 1)
    private final double yOrStance;
    @Serialize(type = Type.DOUBLE, order = 2)
    private final double stanceOrY;
    @Serialize(type = Type.DOUBLE, order = 3)
    private final double z;
    @Serialize(type = Type.FLOAT, order = 4)
    private final float yaw;
    @Serialize(type = Type.FLOAT, order = 5)
    private final float pitch;
    @Serialize(type = Type.BOOL, order = 6)
    private final boolean onGround;

    public PlayerPositionAndLookPacketImpl(double x, double yOrStance, double stanceOrY, double z, float yaw, float pitch, boolean onGround) {
        super();
        this.x = x;
        this.yOrStance = yOrStance;
        this.stanceOrY = stanceOrY;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override
    public int getOpcode() {
        return 0x0D; // packet 13 / 0x0D
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getYOrStance() {
        return yOrStance;
    }

    @Override
    public double getStanceOrY() {
        return stanceOrY;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public float getYaw() {
        return yaw;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public boolean getOnGround() {
        return onGround;
    }

    @Override
    public String getToStringDescription() {
        return String.format("x=\"%f\",yOrStance=\"%f\",stanceOrY=\"%f\",z=\"%f\","
                + "yaw=\"%f\",pitch=\"%f\",onGround=\"%b\"",
                x, yOrStance, stanceOrY, z, yaw, pitch, onGround);
    }
}

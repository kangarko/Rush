package net.rush.packets.packet.impl;

import net.rush.packets.packet.PlayerDiggingPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerDiggingPacketImpl extends AbstractPacket implements PlayerDiggingPacket {
    @Serialize(type = Type.BYTE, order = 0)
    private final byte status;
    @Serialize(type = Type.INT, order = 1)
    private final int x;
    @Serialize(type = Type.BYTE, order = 2)
    private final int y;
    @Serialize(type = Type.INT, order = 3)
    private final int z;
    @Serialize(type = Type.BYTE, order = 4)
    private final byte face;

    public PlayerDiggingPacketImpl(byte status, int x, byte y, int z, byte face) {
        super();
        this.status = status;
        this.x = x;
        this.y = y;
        this.z = z;
        this.face = face;
    }

    @Override
    public int getOpcode() {
        return 0x0E;
    }

    @Override
    public byte getStatus() {
        return status;
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
    public byte getFace() {
        return face;
    }

    @Override
    public String getToStringDescription() {
        return String.format("status=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\",face=\"%d\"", status, x, y, z, face);
    }
}

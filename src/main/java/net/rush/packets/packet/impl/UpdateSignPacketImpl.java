package net.rush.packets.packet.impl;

import net.rush.packets.packet.UpdateSignPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class UpdateSignPacketImpl extends AbstractPacket implements UpdateSignPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int x;
    @Serialize(type = Type.SHORT, order = 1)
    private final short y;
    @Serialize(type = Type.INT, order = 2)
    private final int z;
    @Serialize(type = Type.STRING, order = 3)
    private final String line1;
    @Serialize(type = Type.STRING, order = 4)
    private final String line2;
    @Serialize(type = Type.STRING, order = 5)
    private final String line3;
    @Serialize(type = Type.STRING, order = 6)
    private final String line4;

    public UpdateSignPacketImpl(int x, short y, int z, String line1, String line2, String line3, String line4) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.line4 = line4;
    }

    @Override
    public int getOpcode() {
        return 0x82;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public short getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public String getLine1() {
        return line1;
    }

    @Override
    public String getLine2() {
        return line2;
    }

    @Override
    public String getLine3() {
        return line3;
    }

    @Override
    public String getLine4() {
        return line4;
    }

    @Override
    public String getToStringDescription() {
        return String.format("x=\"%d\",y=\"%d\",z=\"%d\",line1=\"%s\",line2=\"%s\",line3=\"%s\",line4=\"%s\"",
                x, y, z, line1, line2, line3, line4);
    }
}

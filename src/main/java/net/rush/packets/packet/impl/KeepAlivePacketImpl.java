package net.rush.packets.packet.impl;

import net.rush.packets.packet.KeepAlivePacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class KeepAlivePacketImpl extends AbstractPacket implements KeepAlivePacket {
    @Serialize(type = Type.INT, order = 0)
    private final int token;

    public KeepAlivePacketImpl(int id) {
        super();
        this.token = id;
    }

    @Override
    public int getOpcode() {
        return 0x00;
    }

    @Override
    public int getToken() {
        return token;
    }

    @Override
    public String getToStringDescription() {
        return String.format("id=\"%d\"", token);
    }
}

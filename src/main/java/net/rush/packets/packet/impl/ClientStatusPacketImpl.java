package net.rush.packets.packet.impl;

import net.rush.packets.packet.ClientStatusPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ClientStatusPacketImpl extends AbstractPacket implements ClientStatusPacket {
    @Serialize(type = Type.BYTE, order = 0)
    private final byte payload;

    public ClientStatusPacketImpl(byte payload) {
        super();
        this.payload = payload;
    }

    @Override
    public int getOpcode() {
        return 0xCD;
    }

    @Override
    public byte getPayload() {
        return payload;
    }

    @Override
    public String getToStringDescription() {
        return String.format("reason=\"%s\"", payload);
    }
}

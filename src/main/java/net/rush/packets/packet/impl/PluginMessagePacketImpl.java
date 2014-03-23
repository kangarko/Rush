package net.rush.packets.packet.impl;

import net.rush.packets.packet.PluginMessagePacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PluginMessagePacketImpl extends AbstractPacket implements PluginMessagePacket {
    @Serialize(type = Type.STRING, order = 0)
    private final String channel;
    @Serialize(type = Type.SHORT, order = 1)
    private final short length;
    @Serialize(type = Type.BYTE_ARRAY, order = 2, moreInfo = 1)
    private final byte[] data;

    public PluginMessagePacketImpl(String channel, short length, byte[] data) {
        super();
        this.channel = channel;
        this.length = length;
        this.data = data;
    }

    @Override
    public int getOpcode() {
        return 0xFA;
    }

    @Override
    public String getChannel() {
        return channel;
    }

    @Override
    public short getLength() {
        return length;
    }

    @Override
    public byte[] getData() {
        return data;
    }

    @Override
    public String getToStringDescription() {
        return String.format("channel=\"%s\",length=\"%d\",data=byte[%d]", channel, length,
                data.length);
    }
}

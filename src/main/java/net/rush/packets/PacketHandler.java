package net.rush.packets;

import org.jboss.netty.buffer.ChannelBufferInputStream;

public interface PacketHandler<T extends Packet> {
    T handle(ChannelBufferInputStream buf, Class<T> packetType);
}

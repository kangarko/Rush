package net.rush.packets;

import org.jboss.netty.buffer.ChannelBufferOutputStream;

public interface PacketSender<T extends Packet> {
    void send(ChannelBufferOutputStream stream, T packet);
}

package net.rush.packets;

import java.io.DataInputStream;

public interface PacketHandler<T extends Packet> {
    T handle(DataInputStream buf, Class<T> packetType);
}

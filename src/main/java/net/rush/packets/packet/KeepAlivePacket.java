package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface KeepAlivePacket extends Packet {
    int getToken();
}

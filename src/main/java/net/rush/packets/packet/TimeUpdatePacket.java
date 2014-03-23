package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface TimeUpdatePacket extends Packet {
    long getWorldAge();
    long getTime();
}

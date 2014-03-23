package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface IncrementStatisticPacket extends Packet {
    int getStatisticId();
    byte getAmount();
}

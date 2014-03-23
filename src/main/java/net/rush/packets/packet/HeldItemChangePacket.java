package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface HeldItemChangePacket extends Packet {
    short getSlotId();
}

package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface EntityStatusPacket extends Packet {
    int getEntityId();
    byte getStatus();
}

package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface EntityVelocityPacket extends Packet {
    int getEntityId();
    short getVelocityX();
    short getVelocityY();
    short getVelocityZ();
}

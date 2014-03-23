package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface EntityHeadLookPacket extends Packet {
    int getEntityId();
    byte getHeadYaw();
}

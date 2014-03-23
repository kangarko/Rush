package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface EntityLookPacket extends Packet {
    int getEntityId();

    byte getYaw();

    byte getPitch();
}

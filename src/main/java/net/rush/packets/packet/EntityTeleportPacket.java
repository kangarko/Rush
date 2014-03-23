package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface EntityTeleportPacket extends Packet {
    int getEntityId();

    int getX();

    int getY();

    int getZ();

    byte getYaw();

    byte getPitch();
}

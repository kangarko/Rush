package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface EntityLookAndRelMovePacket extends Packet {
    int getEntityId();

    byte getDiffX();

    byte getDiffY();

    byte getDiffZ();

    byte getYaw();

    byte getPitch();
}

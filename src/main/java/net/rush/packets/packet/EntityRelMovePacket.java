package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface EntityRelMovePacket extends Packet {
    int getEntityId();

    byte getDiffX();

    byte getDiffY();

    byte getDiffZ();
}

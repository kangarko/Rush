package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface PlayerPositionPacket extends Packet {
    double getX();

    double getY();

    double getStance();

    double getZ();

    boolean getOnGround();
}

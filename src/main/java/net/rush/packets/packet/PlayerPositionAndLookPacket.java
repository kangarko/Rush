package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface PlayerPositionAndLookPacket extends Packet {
    double getX();

    // client -> server: Y
    // client <- server: stance
    double getYOrStance();

    // client -> server: stance
    // client <- server: Y
    double getStanceOrY();

    double getZ();

    float getYaw();

    float getPitch();

    boolean getOnGround();
}

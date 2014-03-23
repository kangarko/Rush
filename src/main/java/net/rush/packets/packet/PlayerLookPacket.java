package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface PlayerLookPacket extends Packet {
    float getYaw();

    float getPitch();

    boolean getOnGround();
}

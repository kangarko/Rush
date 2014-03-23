package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface PlayerOnGroundPacket extends Packet {
    boolean getOnGround();
}

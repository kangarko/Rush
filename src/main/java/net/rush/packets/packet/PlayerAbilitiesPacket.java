package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface PlayerAbilitiesPacket extends Packet {
    byte getFlags();
    float getFlySpeed();
    float getWalkSpeed();
}

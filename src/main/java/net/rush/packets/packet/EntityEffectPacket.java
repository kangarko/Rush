package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface EntityEffectPacket extends Packet {
    int getEntityId();
    byte getEffectId();
    byte getAmplifier();
    short getDuration();
}

package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface NamedSoundEffectPacket extends Packet {
    String getSoundName();

    int getX();

    int getY();

    int getZ();

    float getVolume();

    byte getPitch();
}

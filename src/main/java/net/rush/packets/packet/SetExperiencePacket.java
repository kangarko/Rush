package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface SetExperiencePacket extends Packet {
    float getExperienceBar();
    short getLevel();
    short getTotalExperience();
}

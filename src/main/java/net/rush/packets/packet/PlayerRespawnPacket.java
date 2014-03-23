package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface PlayerRespawnPacket extends Packet {
    int getDimension();
    byte getDifficulty();
    byte getGameMode();
    short getWorldHeight();
    String getLevelType();
}

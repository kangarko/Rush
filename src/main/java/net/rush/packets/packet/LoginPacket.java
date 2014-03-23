package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface LoginPacket extends Packet {
    int getEntityId();
    
    String getWorldType();

    byte getMode();

    byte getDimension();

    byte getDifficulty();

    int getWorldHeight();

    int getMaxPlayers();
}

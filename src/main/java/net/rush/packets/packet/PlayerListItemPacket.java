package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface PlayerListItemPacket extends Packet {
    String getPlayerName();

    boolean getOnlineStatus();

    short getPing();
}

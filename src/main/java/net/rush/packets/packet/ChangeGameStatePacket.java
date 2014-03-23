package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface ChangeGameStatePacket extends Packet {
    byte getReason();
    byte getGameMode();
}

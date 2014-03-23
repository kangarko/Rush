package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface UseEntityPacket extends Packet {
    int getPlayerEntityId();
    int getTargetEntityId();
    boolean getIsLeftClick();
}

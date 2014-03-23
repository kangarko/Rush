package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface PlayerDiggingPacket extends Packet {
	
	public static final int STATE_START_DIGGING = 0;
	public static final int STATE_DONE_DIGGING = 2;
	public static final int STATE_DROP_ITEM = 4;
	
    byte getStatus();
    int getX();
    int getY();
    int getZ();
    byte getFace();
}

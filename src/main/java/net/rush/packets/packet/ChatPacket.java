package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface ChatPacket extends Packet {
	String getMessage();
	String getPlainMessage();
}

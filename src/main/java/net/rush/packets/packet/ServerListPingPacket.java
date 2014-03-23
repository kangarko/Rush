package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface ServerListPingPacket extends Packet {
	byte getMagic();
}

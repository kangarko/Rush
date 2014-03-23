package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface ClientStatusPacket extends Packet {
	byte getPayload();
}

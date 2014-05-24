package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ServerListPingPacket extends Packet {
	
	public ServerListPingPacket() {
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte magic;

	public ServerListPingPacket(byte magic) {
		this.magic = 1;
	}

	public byte getMagic() {
		return magic;
	}

	public int getOpcode() {
		return 0xFE;
	}

	public String getToStringDescription() {
		return new String();
	}
}

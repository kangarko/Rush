package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ServerListPingPacket extends Packet {
	public ServerListPingPacket() {
		// TODO Auto-generated constructor stub
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

	@Override
	public void read17(ByteBufInputStream input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write17(ByteBufOutputStream output) {
		// TODO Auto-generated method stub

	}
}

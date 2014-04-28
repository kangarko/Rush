package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class HandshakePacket extends Packet {
	@Serialize(type = Type.BYTE, order = 0)
	private final byte protocolVer;
	@Serialize(type = Type.STRING, order = 1)
	private final String username;
	@Serialize(type = Type.STRING, order = 2)
	private final String host;
	@Serialize(type = Type.INT, order = 3)
	private final int port;
	
	public HandshakePacket(byte protocolVer, String username, String host, int port) {
		super();
		this.protocolVer = protocolVer;
		this.username = username;
		this.host = host;
		this.port = port;
	}

	public byte getProtocolVer() {
		return protocolVer;
	}

	public String getUsername() {
		return username;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public int getOpcode() {
		return 0x02;
	}

	public String getToStringDescription() {
		return "message=\"protocol:" + protocolVer + ",nick:" + username + ",host:" + host + ",port:" + port + "\"";
	}
}

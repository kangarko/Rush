package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class HandshakePacket extends Packet {

	public HandshakePacket() {
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte protocolVer;
	@Serialize(type = Type.STRING, order = 1)
	private String username;
	@Serialize(type = Type.STRING, order = 2)
	private String host;
	@Serialize(type = Type.INT, order = 3)
	private int port;

	public int protocolVersion;
	public String serverAddress;
	public int serverPort;
	public int state;

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

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		protocolVersion = readVarInt(input);
		serverAddress = readString(input, 0, false);
		serverPort = input.readUnsignedShort();
		state = readVarInt(input);
	}
}

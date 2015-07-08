package net.rush.protocol.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Handshake extends Packet {

	private int protocolVer;
	private String username;
	private int port;
	private int state;

	@Override
	public void read(ByteBuf in) throws IOException {
		protocolVer = readVarInt(in);
		username = readString(in);
		port = in.readUnsignedShort();
		state = readVarInt(in);
	}
}

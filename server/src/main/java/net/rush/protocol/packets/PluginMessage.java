package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PluginMessage extends Packet {

	private String channel;
	private byte[] data;

	public PluginMessage(String channel, byte[] data) {
		this.channel = channel;
		this.data = data;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		channel = readString(in);
		data = readArrayShort(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(channel, out);
		out.writeShort(data.length);
		out.writeBytes(data);
	}
	
	@Override
	public String toString() {
		return "PluginMessage(channel=" + channel + ", data=" + new String(data, StandardCharsets.UTF_8) + ")";
	}
}

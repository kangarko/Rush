package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;
import net.rush.utils.JsonUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ChatMessage extends Packet {

	private String message;

	@Override
	public void read(ByteBuf input) throws IOException {
		message = readString(input);
	}

	@Override
	public void write(ByteBuf output) throws IOException {
		writeString(JsonUtils.jsonizeChat(message), output);
	}
}

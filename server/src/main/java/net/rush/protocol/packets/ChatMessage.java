package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ChatMessage extends Packet {

	private String message;

	@Override
	public void read(ByteBuf in) throws IOException {
		message = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(message, out);
	}
}

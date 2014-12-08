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
public class KeepAlive extends Packet {

	private int token;

	@Override
	public void read(ByteBuf in) throws IOException {
		token = in.readInt();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeInt(token);
	}
}

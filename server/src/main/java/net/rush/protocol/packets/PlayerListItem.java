package net.rush.protocol.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PlayerListItem extends Packet {

	private String name;
	private boolean online;
	private int ping;

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(name, out);
		out.writeBoolean(online);
		out.writeShort(ping);
	}
}

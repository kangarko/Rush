package net.rush.protocol.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class LoginSuccess extends Packet {

	private String uuid;
	private String name;

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(uuid, out);
		writeString(name, out);
	}

}

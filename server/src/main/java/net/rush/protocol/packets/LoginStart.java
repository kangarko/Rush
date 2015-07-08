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
public class LoginStart extends Packet {

	private String name;

	@Override
	public void read(ByteBuf in) throws IOException {
		name = readString(in);
	}
}

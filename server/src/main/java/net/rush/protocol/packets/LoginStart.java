package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import net.rush.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

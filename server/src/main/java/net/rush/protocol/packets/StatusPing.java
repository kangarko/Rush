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
public class StatusPing extends Packet {

	private long time;

	@Override
	public void read(ByteBuf in) throws IOException {
		time = in.readLong();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(time);
	}

}

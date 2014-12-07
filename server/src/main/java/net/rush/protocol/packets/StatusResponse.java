package net.rush.protocol.packets;

import net.rush.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class StatusResponse extends Packet {

	private String response;
	
	@Override
	public void read(ByteBuf in) {
	}

	@Override
	public void write(ByteBuf out) {
		writeString(response, out);
	}

}

package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class StatusRequest extends Packet {

	@Override
	public void read(ByteBuf in) {
	}

}

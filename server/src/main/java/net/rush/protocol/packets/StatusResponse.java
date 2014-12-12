package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;
import net.rush.protocol.ServerPing;
import net.rush.utils.JsonUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class StatusResponse extends Packet {

	private ServerPing response;

	@Override
	public void write(ByteBuf out) {
		writeString(JsonUtils.jsonizeServerPing(response), out);
	}

}

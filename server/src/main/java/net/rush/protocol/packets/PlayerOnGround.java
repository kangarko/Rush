package net.rush.protocol.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PlayerOnGround extends Packet {

	private boolean onGround;
	protected double x;
	protected double feetY;
	protected double headY;
	protected double z;
	protected float yaw;
	protected float pitch;

	protected PlayerOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		onGround = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeBoolean(onGround);
	}
}

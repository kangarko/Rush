package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PlayerOnGround extends Packet {

	protected boolean onGround;

	protected double x;
	protected double yOrStance;
	protected double stanceOrY;
	protected double z;
	protected float yaw;
	protected float pitch;

	public PlayerOnGround(boolean onGround) {
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

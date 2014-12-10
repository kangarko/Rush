package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@ToString(callSuper=true)
public class PlayerPosition extends PlayerOnGround {

	public PlayerPosition(double x, double y, double stance, double z, boolean onGround) {
		super(onGround);
		this.x = x;
		feetY = y;
		headY = stance;
		this.z = z;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		x = in.readDouble();
		feetY = in.readDouble();
		headY = in.readDouble();
		z = in.readDouble();
		onGround = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeDouble(x);
		out.writeDouble(feetY - 1.62);
		out.writeDouble(headY);
		out.writeDouble(z);
		out.writeBoolean(onGround);
	}
}

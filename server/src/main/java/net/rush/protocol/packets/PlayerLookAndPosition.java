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
public class PlayerLookAndPosition extends PlayerOnGround {

	public PlayerLookAndPosition(double x, double headY, double z, float yaw, float pitch, boolean onGround) {
		super(onGround);
		this.x = x;
		this.feetY = headY;
		this.headY = headY;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		x = in.readDouble();
		feetY = in.readDouble();
		headY = in.readDouble();
		z = in.readDouble();
		
		yaw = in.readFloat();
		pitch = in.readFloat();
		
		onGround = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeDouble(x);
		out.writeDouble(headY);
		out.writeDouble(z);
		out.writeFloat(yaw);
		out.writeFloat(pitch);
		out.writeBoolean(onGround);
	}
}

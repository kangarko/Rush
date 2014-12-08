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

	public PlayerLookAndPosition(double x, double yOrStance, double stanceOrY, double z, float yaw, float pitch, boolean onGround) {
		super(onGround);
		this.x = x;
		this.yOrStance = yOrStance;
		this.stanceOrY = stanceOrY;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		x = in.readDouble();
		yOrStance = in.readDouble();
		stanceOrY = in.readDouble();
		z = in.readDouble();
		
		yaw = in.readFloat();
		pitch = in.readFloat();
		onGround = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeDouble(x);
		out.writeDouble(yOrStance); //feet height ??
		out.writeDouble(z);
		out.writeFloat(yaw);
		out.writeFloat(pitch);
		out.writeBoolean(onGround);
	}
}

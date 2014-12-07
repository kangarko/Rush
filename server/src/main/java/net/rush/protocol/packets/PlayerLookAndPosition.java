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
	public void read(ByteBuf input) throws IOException {
		x = input.readDouble();
		yOrStance = input.readDouble();
		stanceOrY = input.readDouble();
		z = input.readDouble();
		
		yaw = input.readFloat();
		pitch = input.readFloat();
		onGround = input.readBoolean();
	}

	@Override
	public void write(ByteBuf output) throws IOException {
		output.writeDouble(x);
		output.writeDouble(yOrStance); //feet height ??
		output.writeDouble(z);
		output.writeFloat(yaw);
		output.writeFloat(pitch);
		output.writeBoolean(onGround);
	}
}

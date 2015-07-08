package net.rush.protocol.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.rush.entity.EntityPlayer;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@ToString(callSuper=true)
public class PlayerLookAndPosition extends PlayerOnGround {

	public PlayerLookAndPosition(double x, double feetY, double z, float yaw, float pitch, boolean onGround) {
		super(onGround);
		this.x = x;
		this.feetY = feetY;
		this.headY = feetY;
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
		
		super.read(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeDouble(x);		
		out.writeDouble(feetY + 1 + EntityPlayer.NORMAL_EYE_HEIGHT); // TODO why +1 ?
		out.writeDouble(z);
		out.writeFloat(yaw);
		out.writeFloat(pitch);
		super.write(out);
	}
}
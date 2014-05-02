package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerLookPacket extends Packet {

	public PlayerLookPacket() {
	}

	@Serialize(type = Type.FLOAT, order = 0)
	private float yaw;
	@Serialize(type = Type.FLOAT, order = 1)
	private float pitch;
	@Serialize(type = Type.BOOL, order = 2)
	private boolean onGround;

	public PlayerLookPacket(float yaw, float pitch, boolean onGround) {
		super();
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
	}

	public int getOpcode() {
		return 0x0C;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public boolean getOnGround() {
		return onGround;
	}

	public String getToStringDescription() {
		return String.format("yaw=\"%f\",pitch=\"%f\",onGround=\"%b\"", yaw, pitch, onGround);
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		yaw = input.readFloat();
		pitch = input.readFloat();
		onGround = input.readBoolean();
	}
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeFloat(yaw);
		output.writeFloat(pitch);
		output.writeBoolean(onGround);
	}

}

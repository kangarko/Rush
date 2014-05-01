package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerOnGroundPacket extends Packet {

	public PlayerOnGroundPacket() {
	}

	@Serialize(type = Type.BOOL, order = 0)
	private boolean onGround;

	public PlayerOnGroundPacket(boolean onGround) {
		super();
		this.onGround = onGround;
	}

	public int getOpcode() {
		return 0x0A;
	}

	public boolean getOnGround() {
		return onGround;
	}

	public String getToStringDescription() {
		return String.format("onGround=\"%b\"", onGround);
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		onGround = input.readBoolean();
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeBoolean(onGround);
	}
}

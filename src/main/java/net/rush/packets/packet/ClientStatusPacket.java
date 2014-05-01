package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ClientStatusPacket extends Packet {

	public ClientStatusPacket() {
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte actionId;

	public ClientStatusPacket(byte actionId) {
		super();
		this.actionId = actionId;
	}

	public int getOpcode() {
		return 0xCD;
	}

	public byte getActionId() {
		return actionId;
	}

	public String getToStringDescription() {
		return String.format("actionId=\"%s\"", actionId);
	}
	
	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		actionId = input.readByte();
	}
}

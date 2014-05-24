package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class HeldItemChangePacket extends Packet {
	public HeldItemChangePacket() {
	}

	@Serialize(type = Type.SHORT, order = 0)
	private short slotId;

	public HeldItemChangePacket(short slotId) {
		super();
		this.slotId = slotId;
	}

	public int getOpcode() {
		return 0x10;
	}

	public short getSlotId() {
		return slotId;
	}

	public String getToStringDescription() {
		return String.format("slotId=\"%d\"", slotId);
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		slotId = input.readShort();
	}
}

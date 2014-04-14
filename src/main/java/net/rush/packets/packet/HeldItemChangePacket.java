package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class HeldItemChangePacket extends Packet {
	@Serialize(type = Type.SHORT, order = 0)
	private final short slotId;

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
}

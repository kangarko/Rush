package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ItemCollectPacket extends Packet {
	public ItemCollectPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.INT, order = 0)
	private int collected;
	@Serialize(type = Type.INT, order = 1)
	private int collector;

	public ItemCollectPacket(int collected, int collector) {
		super();
		this.collected = collected;
		this.collector = collector;
	}

	public int getOpcode() {
		return 0x16;
	}

	public int getCollected() {
		return collected;
	}

	public int getCollector() {
		return collector;
	}

	public String getToStringDescription() {
		return String.format("collected=\"%d\",collector=\"%d\"", collected, collector);
	}

}

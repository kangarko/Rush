package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PluginMessagePacket extends Packet {
	@Serialize(type = Type.STRING, order = 0)
	private final String channel;
	@Serialize(type = Type.SHORT, order = 1)
	private final short length;
	@Serialize(type = Type.BYTE_ARRAY, order = 2, moreInfo = 1)
	private final byte[] data;

	public PluginMessagePacket(String channel, short length, byte[] data) {
		super();
		this.channel = channel;
		this.length = length;
		this.data = data;
	}

	public int getOpcode() {
		return 0xFA;
	}

	public String getChannel() {
		return channel;
	}

	public short getLength() {
		return length;
	}

	public byte[] getData() {
		return data;
	}

	public String getToStringDescription() {
		return String.format("channel=\"%s\",length=\"%d\",data=byte[%d]", channel, length, data.length);
	}
}

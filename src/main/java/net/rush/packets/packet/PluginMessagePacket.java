package net.rush.packets.packet;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PluginMessagePacket extends Packet {

	public PluginMessagePacket() {}

	@Serialize(type = Type.STRING, order = 0)
	private String channel;
	@Serialize(type = Type.SHORT, order = 1)
	private short length;
	@Serialize(type = Type.BYTE_ARRAY, order = 2, moreInfo = 1)
	private byte[] data;

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
		return String.format("channel=\"%s\",length=\"%d\",data=byte[%d]",
				channel, length, data.length);
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		channel = readString(input, 999999999, false);
		length = input.readShort();
		byte[] bytes = new byte[1];
		input.readFully(bytes);
		data = bytes;
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		writeString(channel, output, false);
		output.writeShort(length);
		output.write(data);
	}
}

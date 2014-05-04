package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class UpdateWindowPropertyPacket extends Packet {
	
	public UpdateWindowPropertyPacket() {
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte windowId;
	@Serialize(type = Type.SHORT, order = 1)
	private short property;
	@Serialize(type = Type.SHORT, order = 2)
	private short value;

	public UpdateWindowPropertyPacket(byte windowId, short property, short value) {
		super();
		this.windowId = windowId;
		this.property = property;
		this.value = value;
	}

	public int getOpcode() {
		return 0x69;
	}

	public byte getWindowId() {
		return windowId;
	}

	public short getProperty() {
		return property;
	}

	public short getValue() {
		return value;
	}

	public String getToStringDescription() {
		return String.format("windowId=\"%d\",property=\"%d\",value=\"%d\"", windowId, property, value);
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeByte(windowId);
		output.writeShort(property);
		output.writeShort(value);
	}
}

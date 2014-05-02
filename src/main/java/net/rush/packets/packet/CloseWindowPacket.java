package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class CloseWindowPacket extends Packet {
	
	public CloseWindowPacket() {
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte windowId;

	public CloseWindowPacket(byte windowId) {
		super();
		this.windowId = windowId;
	}

	public int getOpcode() {
		return 0x65;
	}

	public byte getWindowId() {
		return windowId;
	}

	public String getToStringDescription() {
		return String.format("windowId=\"%d\"", windowId);
	}
	
	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		windowId = (byte) input.readUnsignedByte();
	}
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeByte(windowId);
	}

}

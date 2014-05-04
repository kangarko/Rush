package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class OpenWindowPacket extends Packet {
	
	public OpenWindowPacket() {
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte windowId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte inventoryType;
	@Serialize(type = Type.STRING, order = 2)
	private String windowTitle;
	@Serialize(type = Type.BYTE, order = 3)
	private byte numberOfSlots;
	@Serialize(type = Type.BOOL, order = 4)
	private boolean useProvidedWindowTitle;
	@Serialize(type = Type.INT, order = 5)
	private int horseId;

	public OpenWindowPacket(byte windowId, byte inventoryType, String windowTitle, byte numberOfSlots, boolean useProvidedWindowTitle, int horseId) {
		super();
		this.windowId = windowId;
		this.inventoryType = inventoryType;
		this.windowTitle = windowTitle;
		this.numberOfSlots = numberOfSlots;
		this.useProvidedWindowTitle = useProvidedWindowTitle;
		this.horseId = horseId;
	}

	public int getOpcode() {
		return 0x64;
	}

	public byte getWindowId() {
		return windowId;
	}

	public byte getInventoryType() {
		return inventoryType;
	}

	public String getWindowTitle() {
		return windowTitle;
	}

	public byte getNumberOfSlots() {
		return numberOfSlots;
	}

	public boolean getUseProvidedWindowTitle() {
		return useProvidedWindowTitle;
	}

	public int getHorseId() {
		return horseId;
	}

	public String getToStringDescription() {
		return String.format("windowId=\"%d\",inventoryType=\"%d\",windowTitle=\"%s\",numberOfSlots=\"%d\",useProvidedTitle=\"%b\",horseIdé\"%d\"", windowId, inventoryType, windowTitle, numberOfSlots, useProvidedWindowTitle, horseId);
	}
	
	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		windowId = (byte) input.readUnsignedByte();
		inventoryType = (byte) input.readUnsignedByte();
		windowTitle = readString(input, 33, false);
		numberOfSlots = (byte) input.readUnsignedByte();
		useProvidedWindowTitle = input.readBoolean();
		horseId = input.readInt();
	}
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeByte(windowId);
		output.writeByte(inventoryType);
		writeString(windowTitle, output, false);
		output.writeByte(numberOfSlots);
		output.writeBoolean(useProvidedWindowTitle);
		output.writeInt(horseId);
	}

}

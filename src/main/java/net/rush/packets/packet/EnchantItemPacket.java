package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EnchantItemPacket extends Packet {
	
	public EnchantItemPacket() {
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte windowId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte enchantment;

	public EnchantItemPacket(byte windowId, byte enchantment) {
		super();
		this.windowId = windowId;
		this.enchantment = enchantment;
	}

	public int getOpcode() {
		return 0x6C;
	}

	public byte getWindowId() {
		return windowId;
	}

	public byte getEnchantment() {
		return enchantment;
	}

	public String getToStringDescription() {
		return String.format("windowId=\"%d\",enchantment=\"%d\"", windowId, enchantment);
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		windowId = input.readByte();
		enchantment = input.readByte();
	}
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeByte(windowId);
		output.writeByte(enchantment);
	}
}

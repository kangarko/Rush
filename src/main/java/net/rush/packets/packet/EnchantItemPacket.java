package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EnchantItemPacket extends Packet {
	public EnchantItemPacket() {
		// TODO Auto-generated constructor stub
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
		return String.format("windowId=\"%d\",enchantment=\"%d\"", windowId,
				enchantment);
	}

	@Override
	public void read18(ByteBufInputStream input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write18(ByteBufOutputStream output) {
		// TODO Auto-generated method stub

	}
}

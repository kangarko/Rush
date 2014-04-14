package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EnchantItemPacket extends Packet {
	@Serialize(type = Type.BYTE, order = 0)
	private final byte windowId;
	@Serialize(type = Type.BYTE, order = 1)
	private final byte enchantment;

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
}

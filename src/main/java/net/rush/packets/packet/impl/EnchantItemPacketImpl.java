package net.rush.packets.packet.impl;

import net.rush.packets.packet.EnchantItemPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EnchantItemPacketImpl extends AbstractPacket implements EnchantItemPacket {
    @Serialize(type = Type.BYTE, order = 0)
    private final byte windowId;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte enchantment;

    public EnchantItemPacketImpl(byte windowId, byte enchantment) {
        super();
        this.windowId = windowId;
        this.enchantment = enchantment;
    }

    @Override
    public int getOpcode() {
        return 0x6C;
    }

    @Override
    public byte getWindowId() {
        return windowId;
    }

    @Override
    public byte getEnchantment() {
        return enchantment;
    }

    @Override
    public String getToStringDescription() {
        return String.format("windowId=\"%d\",enchantment=\"%d\"", windowId, enchantment);
    }
}

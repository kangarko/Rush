package net.rush.packets.packet.impl;

import net.rush.packets.packet.TabCompletePacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class TabCompletePacketImpl extends AbstractPacket implements TabCompletePacket {
    @Serialize(type = Type.STRING, order = 0)
    private final String text;

    public TabCompletePacketImpl(String text) {
        super();
        this.text = text;
    }

    @Override
    public int getOpcode() {
        return 0xCB;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getToStringDescription() {
        return String.format("text=\"%s\"", text);
    }
}

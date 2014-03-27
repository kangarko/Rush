package net.rush.packets.packet.impl;

import net.rush.packets.packet.ClientSettingsPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ClientSettingsPacketImpl extends AbstractPacket implements ClientSettingsPacket {
    @Serialize(type = Type.STRING, order = 0)
    private final String locale;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte viewDistance;
    @Serialize(type = Type.BYTE, order = 2)
    private final byte chatFlags;
    @Serialize(type = Type.BYTE, order = 3)
    private final byte difficulty;
    @Serialize(type = Type.BOOL, order = 4)
    private final boolean showCape;

    public ClientSettingsPacketImpl(String locale, byte viewDistance, byte chatFlags, byte difficulty, boolean showCape) {
        super();
        this.locale = locale;
        this.viewDistance = viewDistance;
        this.chatFlags = chatFlags;
        this.difficulty = difficulty;
        this.showCape = showCape;
    }

    @Override
    public int getOpcode() {
        return 0xCC;
    }

    @Override
    public String getLocale() {
        return locale;
    }
    
    @Override
    public byte getViewDistance() {
        return viewDistance;
    }
    
    @Override
    public byte getChatFlags() {
        return chatFlags;
    }
    
    @Override
    public byte getDifficulty() {
        return difficulty;
    }

    @Override
    public boolean getShowCape() {
        return showCape;
    }
    
    @Override
    public String getToStringDescription() {
        return String.format("locale=%s,viewDistance=%d,chatFlags=%d,difficulty=%d", locale, viewDistance, chatFlags, difficulty);
    }
}

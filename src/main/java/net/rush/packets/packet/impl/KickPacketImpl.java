package net.rush.packets.packet.impl;

import net.rush.packets.packet.KickPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class KickPacketImpl extends AbstractPacket implements KickPacket {
    @Serialize(type = Type.STRING, order = 0)
    private final String reason;

    /**
     * @deprecated Throws PlayerGetMadException
     */
    public KickPacketImpl() {
        super();
        this.reason = "You are a faggot. Piss off.";
    }
    
    public KickPacketImpl(String reason) {
        super();
        this.reason = reason;
    }

    @Override
    public int getOpcode() {
        return 0xFF;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public String getToStringDescription() {
        return String.format("reason=\"%s\"", reason);
    }
}

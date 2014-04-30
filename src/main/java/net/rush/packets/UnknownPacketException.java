package net.rush.packets;

import java.io.IOException;

public class UnknownPacketException extends IOException {
    private static final long serialVersionUID = 1L;

    public UnknownPacketException(int code) {
        super(createMessage(code));
    }

    public UnknownPacketException(int code, Throwable cause) {
        super(createMessage(code), cause);
    }

    public UnknownPacketException(Class<? extends Packet> clazz) {
        super(createMessage(clazz));
    }

    
    private static String createMessage(int code) {
        return String.format("Couldn't find packet %1$d (0x0%1$X)", code);
    }
    
    private static String createMessage(Class<? extends Packet> clazz) {
        return "Couldn't find packet " + clazz.getSimpleName();
    }
}

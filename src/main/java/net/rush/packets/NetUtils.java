package net.rush.packets;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public final class NetUtils {
    private NetUtils() {
    }
    
    public static String readString(DataInput stream, int maxlength) throws IOException {
        short recvlength = stream.readShort();
        if (recvlength > maxlength)
            throw new IOException("String longer than allowed length (" + recvlength + ")!");
        if (recvlength < 0)
            throw new IOException("A string shorter than 0??? (" + recvlength + ")");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < recvlength; i++) {
            sb.append(stream.readChar());
        }

        return sb.toString();
    }

    public static void writeString(DataOutput stream, String string) throws IOException {
        stream.writeShort(string.length());
        stream.writeChars(string);
    }
}

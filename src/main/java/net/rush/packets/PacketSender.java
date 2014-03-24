package net.rush.packets;

import java.io.DataOutputStream;

public interface PacketSender<T extends Packet> {
    void send(DataOutputStream stream, T packet);
}

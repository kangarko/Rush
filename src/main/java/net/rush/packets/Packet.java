package net.rush.packets;

public interface Packet {
    int getOpcode();
    /**
     * @return The interface in {@code tk.maincraft.util.mcpackets.packet} this packet implements.
     */
    Class<? extends Packet> getPacketType();
}

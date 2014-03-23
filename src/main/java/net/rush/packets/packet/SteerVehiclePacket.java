package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface SteerVehiclePacket extends Packet {
    float getSideways();
    float getForward();
    boolean jump();
    boolean unmount();
}

package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface EntityActionPacket extends Packet {
	
	public static final int ACTION_CROUCH = 1;
	public static final int ACTION_UNCROUCH = 2;
	public static final int ACTION_LEAVE_BED = 3;
	public static final int START_SPRINTING = 4;
	public static final int STOP_SPRINTING = 5;
	
    int getEntityId();
    byte getActionId();
    int getHorseJumpBoost();
}

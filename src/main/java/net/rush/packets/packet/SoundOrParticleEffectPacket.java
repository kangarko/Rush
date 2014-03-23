package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface SoundOrParticleEffectPacket extends Packet {
	
	public static final int CLICK2 = 1000;

	public static final int CLICK1 = 1001;

	public static final int BOW_FIRE = 1002;

	public static final int RECORD_PLAY = 1005;

	public static final int DIG_SOUND = 2001;
	
    int getEffectId();
    int getX();
    byte getY();
    int getZ();
    int getData();
    boolean getRelativeVolume();
}

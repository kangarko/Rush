package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class NamedSoundEffectPacket extends Packet {
	@Serialize(type = Type.STRING, order = 0)
	private final String soundName;
	@Serialize(type = Type.INT, order = 1)
	private final int x;
	@Serialize(type = Type.INT, order = 2)
	private final int y;
	@Serialize(type = Type.INT, order = 3)
	private final int z;
	@Serialize(type = Type.FLOAT, order = 4)
	private final float volume;
	@Serialize(type = Type.BYTE, order = 5)
	private final byte pitch;

	public NamedSoundEffectPacket(String soundName, double x, double y, double z, float volume, float pitch) {
		super();		
		
		if (pitch < 0)
			pitch = 0;

		if (pitch > 255)
			pitch = 255;
		
		this.soundName = soundName;
		this.x = (int) (x * 8D);
		this.y = (int) (y * 8D);
		this.z = (int) (z * 8D);
		this.volume = volume;
		this.pitch = (byte) (pitch * 63.0F);
	}
	
	public int getOpcode() {
		return 0x3E;
	}

	public String getSoundName() {
		return soundName;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public float getVolume() {
		return volume;
	}

	public byte getPitch() {
		return pitch;
	}

	public String getToStringDescription() {
		return String.format("soundName=\"%s\",x=\"%s\",y=\"%s\",z=\"%s\",volume=\"%s\",pitch=\"%s\"", soundName, x, y, z, volume, pitch);
	}
}

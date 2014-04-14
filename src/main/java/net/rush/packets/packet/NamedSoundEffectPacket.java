package net.rush.packets.packet;

import net.rush.model.Position;
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

	public NamedSoundEffectPacket(String soundName, Position pos, float volume, byte pitch) {
		super();
		this.soundName = soundName;
		x = (int) pos.getX();
		y = (int) pos.getY();
		z = (int) pos.getZ();
		this.volume = volume;
		this.pitch = pitch;
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
		return String.format("soundName=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\",volume=\"%d\",pitch=\"%d\"", soundName, x, y, z, volume, pitch);
	}
}

package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import org.bukkit.Effect;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SoundOrParticleEffectPacket extends Packet {

	public SoundOrParticleEffectPacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int effectId;
	@Serialize(type = Type.INT, order = 1)
	private int x;
	@Serialize(type = Type.BYTE, order = 2)
	private byte y;
	@Serialize(type = Type.INT, order = 3)
	private int z;
	@Serialize(type = Type.INT, order = 4)
	private int data;
	@Serialize(type = Type.BOOL, order = 5)
	private boolean relativeVolume;

	@SuppressWarnings("deprecation")
	public SoundOrParticleEffectPacket(Effect effect, int x, int y, int z, int data) {
		this(effect.getId(), x, y, z, data, false);
	}
	
	public SoundOrParticleEffectPacket(int effectId, int x, int y, int z, int data, boolean relativeVolume) {
		super();
		this.effectId = effectId;
		this.x = x;
		this.y = (byte)y;
		this.z = z;
		this.data = data;
		this.relativeVolume = relativeVolume;
	}

	public int getOpcode() {
		return 0x3D;
	}

	public int getEffectId() {
		return effectId;
	}

	public int getX() {
		return x;
	}

	public byte getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public int getData() {
		return data;
	}

	public boolean getRelativeVolume() {
		return relativeVolume;
	}

	public String getToStringDescription() {
		return String.format("effectId=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\",data=\"%d\"", effectId, x, y, z, data);
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeInt(effectId);
		output.writeInt(x);
		output.writeByte(y);
		output.writeInt(z);
		output.writeInt(data);
		output.writeBoolean(relativeVolume);
	}
}

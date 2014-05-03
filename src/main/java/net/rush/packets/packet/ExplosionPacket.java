package net.rush.packets.packet;

import java.util.Set;

import net.rush.model.Position;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ExplosionPacket extends Packet {
	public ExplosionPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.DOUBLE, order = 0)
	private double x;
	@Serialize(type = Type.DOUBLE, order = 1)
	private double y;
	@Serialize(type = Type.DOUBLE, order = 2)
	private double z;
	@Serialize(type = Type.FLOAT, order = 3)
	private float size;
	@Serialize(type = Type.BLOCKCOORD_COLLECTION, order = 4)
	private Set<Position> destroyedBlocks;

	public ExplosionPacket(double x, double y, double z, float size, Set<Position> destroyedBlocks) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.size = size;
		this.destroyedBlocks = destroyedBlocks;
	}

	public int getOpcode() {
		return 0x3C;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public float getSize() {
		return size;
	}

	public Set<Position> getDestroyedBlocks() {
		return destroyedBlocks;
	}

	public String getToStringDescription() {
		return String.format("x=\"%d\",y=\"%d\",z=\"%d\",size=\"%d\",destroyedBlocks=\"%s\"", x, y, z, size, destroyedBlocks);
	}

}

package net.rush.packets.packet;

import java.util.Set;

import net.rush.model.Coordinate;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ExplosionPacket extends Packet {
	@Serialize(type = Type.DOUBLE, order = 0)
	private final double x;
	@Serialize(type = Type.DOUBLE, order = 1)
	private final double y;
	@Serialize(type = Type.DOUBLE, order = 2)
	private final double z;
	@Serialize(type = Type.FLOAT, order = 3)
	private final float size;
	@Serialize(type = Type.BLOCKCOORD_COLLECTION, order = 4)
	private final Set<Coordinate> destroyedBlocks;

	public ExplosionPacket(double x, double y, double z, float size, Set<Coordinate> destroyedBlocks) {
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

	public Set<Coordinate> getDestroyedBlocks() {
		return destroyedBlocks;
	}

	public String getToStringDescription() {
		return String.format("x=\"%d\",y=\"%d\",z=\"%d\",size=\"%d\",destroyedBlocks=\"%s\"", x, y, z, size, destroyedBlocks);
	}
}

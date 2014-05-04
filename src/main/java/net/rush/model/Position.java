package net.rush.model;

import net.rush.util.StringUtils;

import org.bukkit.util.NumberConversions;

public final class Position {
	public static final int GRANULARITY = 32;
	public static final Position ZERO = new Position(0.0D, 0.0D, 0.0D);
	
	private final double x, y, z;

	public Position(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
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

	public int getPixelX() {
		return (int) (x * 32.0D);
	}

	public int getPixelY() {
		return (int) (y * 32.0D);
	}

	public int getPixelZ() {
		return (int) (z * 32.0D);
	}

	public int hashCode() {
		int result = 1;

		long temp = Double.doubleToLongBits(x);
		result = 31 * result + (int) (temp ^ temp >>> 32);
		temp = Double.doubleToLongBits(y);
		result = 31 * result + (int) (temp ^ temp >>> 32);
		temp = Double.doubleToLongBits(z);
		result = 31 * result + (int) (temp ^ temp >>> 32);
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
	
    public double distance(Position o) {
        return Math.sqrt(distanceSquared(o));
    }
    
    public double distanceSquared(Position o) {
        if (o == null)
            throw new IllegalArgumentException("Cannot measure distance to a null location");

        return NumberConversions.square(x - o.x) + NumberConversions.square(y - o.y) + NumberConversions.square(z - o.z);
    }
	
	@Override
	public String toString() {
		return StringUtils.serializeLoc((int)x, (int)y, (int)z);
	}
}
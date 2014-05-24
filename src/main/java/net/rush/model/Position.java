package net.rush.model;

import net.rush.util.StringUtils;

import org.bukkit.util.NumberConversions;

public final class Position {
	
	/**
	 * The number of integer values between each double value. For example, if
	 * the coordinate was {@code 1.5}, this would be sent as
	 * {@code 1.5 * 32 = 48} within certain packets. This is sometimes called
	 * the number of pixels in a block, as each block uses a 32x32 texture.
	 */
	public static final int GRANULARITY = 32;
	public static final Position ZERO = new Position(0.0D, 0.0D, 0.0D);
	
	public final double x, y, z;

	public Position(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getPixelX() {
		return (int) (x * GRANULARITY);
	}

	public int getPixelY() {
		return (int) (y * GRANULARITY);
	}

	public int getPixelZ() {
		return (int) (z * GRANULARITY);
	}

	public int hashCode() {
		int result = 1;

		long temp = Double.doubleToLongBits(x);
		result = 31 * result + (int) (temp ^ temp >>> GRANULARITY);
		temp = Double.doubleToLongBits(y);
		result = 31 * result + (int) (temp ^ temp >>> GRANULARITY);
		temp = Double.doubleToLongBits(z);
		result = 31 * result + (int) (temp ^ temp >>> GRANULARITY);
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
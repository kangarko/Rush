package net.rush.model;

import lombok.ToString;

@ToString
public final class Position {

	/**
	 * The number of integer values between each double value. For example, if
	 * the coordinate was {@code 1.5}, this would be sent as
	 * {@code 1.5 * 32 = 48} within certain packets. This is sometimes called
	 * the number of pixels in a block, as each block uses a 32x32 texture.
	 */
	public static final int GRANULARITY = 32;
	public static final Position ZERO = new Position(0.0D, 0.0D, 0.0D);

	public double x, y, z;

	public Position(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int intX() {
		return (int) x;
	}

	public int intY() {
		return (int) y;
	}

	public int intZ() {
		return (int) z;
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

	@Override
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

	@Override
	public boolean equals(Object obj) {
		Position other = (Position) obj;
		
		return x == other.x && y == other.y && z == other.z;
	}

	public double distance(Position o) {
		return Math.sqrt(distanceSquared(o));
	}

	public double distanceSquared(Position o) {
		if (o == null)
			throw new IllegalArgumentException("Cannot measure distance to a null location");

		return Math.sqrt(x - o.x) + Math.sqrt(y - o.y) + Math.sqrt(z - o.z);
	}
}
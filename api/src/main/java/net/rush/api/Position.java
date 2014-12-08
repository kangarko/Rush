package net.rush.api;


public final class Position {

	/**
	 * The number of integer values between each double value. For example, if
	 * the coordinate was {@code 1.5}, this would be sent as
	 * {@code 1.5 * 32 = 48} within certain packets. This is sometimes called
	 * the number of pixels in a block, as each block uses a 32x32 texture.
	 */
	public static final int GRANULARITY = 32;
	public static final Position ZERO = new Position(0.0D, 0.0D, 0.0D);

	public double x, y, z, yaw, pitch;

	public Position(double x, double y, double z) {
		this(x, y, z, 0, 0);
	}
	
	public Position(double x, double y, double z, double yaw, double pitch) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
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
	
	/**
	 * Gets an integer approximation of the yaw between 0 and 255.
	 * @return An integer approximation of the yaw.
	 */
	public int getIntYaw() {
		return (int) (((yaw % 360) / 360) * 256);
	}

	/**
	 * Gets an integer approximation of the pitch between 0 and 255.
	 * @return An integer approximation of the pitch.
	 */
	public int getIntPitch() {
		return (int) (((pitch % 360) / 360) * 256);
	}

	public void setRotation(double yaw, double pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
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
		if (!(obj instanceof Position))
			return false;
		
		Position other = (Position) obj;
		
		return x == other.x && y == other.y && z == other.z && yaw == other.yaw && pitch == other.pitch;
	}
	
	public boolean equalsPosition(Object obj) {
		if (!(obj instanceof Position))
			return false;
		
		Position other = (Position) obj;
		
		return x == other.x && y == other.y && z == other.z;
	}
	
	public boolean equalsRotation(Object obj) {
		if (!(obj instanceof Position))
			return false;
		
		Position other = (Position) obj;
		
		return yaw == other.yaw && pitch == other.pitch;
	}

	public double distance(Position o) {
		return Math.sqrt(distanceSquared(o));
	}

	public double distanceSquared(Position o) {
		if (o == null)
			throw new IllegalArgumentException("Cannot measure distance to a null location");

		return Math.sqrt(x - o.x) + Math.sqrt(y - o.y) + Math.sqrt(z - o.z);
	}
	
	@Override
	public String toString() {
		return "Position{x=" + x +",y=" + y + ",z=" + z + "}";
	}
}
package net.rush.model.misc;

import net.rush.util.MathHelper;

public class Vec3 {
	
	/**
	 * A global Vec3Pool that always creates new vectors instead of reusing them and is thread-safe.
	 */
	public static final Vec3Pool emptyPool = new Vec3Pool(-1, -1);
	
	public final Vec3Pool myVec3LocalPool;

	/** X coordinate of Vec3D */
	public double xCoord;

	/** Y coordinate of Vec3D */
	public double yCoord;

	/** Z coordinate of Vec3D */
	public double zCoord;

	/**
	 * Static method for creating a new Vec3D given the three x,y,z values. This is only called from the other static
	 * method which creates and places it in the list.
	 */
	public static Vec3 createVectorHelper(double x, double y, double z) {
		return new Vec3(emptyPool, x, y, z);
	}

	protected Vec3(Vec3Pool vector, double x, double y, double z) {
		if (x == -0.0D) {
			x = 0.0D;
		}

		if (y == -0.0D) {
			y = 0.0D;
		}

		if (z == -0.0D) {
			z = 0.0D;
		}

		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		this.myVec3LocalPool = vector;
	}

	/**
	 * Sets the x,y,z components of the vector as specified.
	 */
	protected Vec3 setComponents(double x, double y, double z) {
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		return this;
	}

	/**
	 * Normalizes the vector to a length of 1 (except if it is the zero vector)
	 */
	public Vec3 normalize() {
		double sqrted = (double) MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
		return sqrted < 1.0E-4D ? this.myVec3LocalPool.getVecFromPool(0.0D, 0.0D, 0.0D) : this.myVec3LocalPool.getVecFromPool(this.xCoord / sqrted, this.yCoord / sqrted, this.zCoord / sqrted);
	}

	public double dotProduct(Vec3 vector) {
		return this.xCoord * vector.xCoord + this.yCoord * vector.yCoord + this.zCoord * vector.zCoord;
	}

	/**
	 * Adds the specified x,y,z vector components to this vector and returns the resulting vector. Does not change this
	 * vector.
	 */
	public Vec3 addVector(double x, double y, double z) {
		return this.myVec3LocalPool.getVecFromPool(this.xCoord + x, this.yCoord + y, this.zCoord + z);
	}

	/**
	 * Euclidean distance between this and the specified vector, returned as double.
	 */
	public double distanceTo(Vec3 vector) {
		double x = vector.xCoord - this.xCoord;
		double y = vector.yCoord - this.yCoord;
		double z = vector.zCoord - this.zCoord;
		return (double) MathHelper.sqrt_double(x * x + y * y + z * z);
	}

	/**
	 * The square of the Euclidean distance between this and the specified vector.
	 */
	public double squareDistanceTo(Vec3 vector) {
		double var2 = vector.xCoord - this.xCoord;
		double var4 = vector.yCoord - this.yCoord;
		double var6 = vector.zCoord - this.zCoord;
		return var2 * var2 + var4 * var4 + var6 * var6;
	}

	/**
	 * The square of the Euclidean distance between this and the vector of x,y,z components passed in.
	 */
	public double squareDistanceTo(double x, double y, double z) {
		double newX = x - this.xCoord;
		double newY = y - this.yCoord;
		double newZ = z - this.zCoord;
		return newX * newX + newY * newY + newZ * newZ;
	}

	/**
	 * Returns the length of the vector.
	 */
	public double lengthVector() {
		return (double) MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
	}

	/**
	 * Returns a new vector with x value equal to the second parameter, along the line between this vector and the
	 * passed in vector, or null if not possible.
	 */
	public Vec3 getIntermediateWithXValue(Vec3 vector, double x) {
		double vecX = vector.xCoord - this.xCoord;
		double vecY = vector.yCoord - this.yCoord;
		double vecZ = vector.zCoord - this.zCoord;

		if (vecX * vecX < 1.0000000116860974E-7D) {
			return null;
		} else {
			double finalX = (x - this.xCoord) / vecX;
			return finalX >= 0.0D && finalX <= 1.0D ? this.myVec3LocalPool.getVecFromPool(this.xCoord + vecX * finalX, this.yCoord + vecY * finalX, this.zCoord + vecZ * finalX) : null;
		}
	}

	/**
	 * Returns a new vector with y value equal to the second parameter, along the line between this vector and the
	 * passed in vector, or null if not possible.
	 */
	public Vec3 getIntermediateWithYValue(Vec3 vector, double y) {
		double vecX = vector.xCoord - this.xCoord;
		double vecY = vector.yCoord - this.yCoord;
		double vecZ = vector.zCoord - this.zCoord;

		if (vecY * vecY < 1.0000000116860974E-7D) {
			return null;
		} else {
			double finalY = (y - this.yCoord) / vecY;
			return finalY >= 0.0D && finalY <= 1.0D ? this.myVec3LocalPool.getVecFromPool(this.xCoord + vecX * finalY, this.yCoord + vecY * finalY, this.zCoord + vecZ * finalY) : null;
		}
	}

	/**
	 * Returns a new vector with z value equal to the second parameter, along the line between this vector and the
	 * passed in vector, or null if not possible.
	 */
	public Vec3 getIntermediateWithZValue(Vec3 vector, double z) {
		double vecX = vector.xCoord - this.xCoord;
		double vecY = vector.yCoord - this.yCoord;
		double vecZ = vector.zCoord - this.zCoord;

		if (vecZ * vecZ < 1.0000000116860974E-7D) {
			return null;
		} else {
			double finalZ = (z - this.zCoord) / vecZ;
			return finalZ >= 0.0D && finalZ <= 1.0D ? this.myVec3LocalPool.getVecFromPool(this.xCoord + vecX * finalZ, this.yCoord + vecY * finalZ, this.zCoord + vecZ * finalZ) : null;
		}
	}

	public String toString() {
		return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
	}

	/**
	 * Rotates the vector around the x axis by the specified angle.
	 */
	public void rotateAroundX(float x) {
		float cos = MathHelper.cos(x);
		float sin = MathHelper.sin(x);
		double newY = this.yCoord * (double) cos + this.zCoord * (double) sin;
		double newZ = this.zCoord * (double) cos - this.yCoord * (double) sin;
		this.yCoord = newY;
		this.zCoord = newZ;
	}

	/**
	 * Rotates the vector around the y axis by the specified angle.
	 */
	public void rotateAroundY(float y) {
		float cos = MathHelper.cos(y);
		float sin = MathHelper.sin(y);
		double newX = this.xCoord * (double) cos + this.zCoord * (double) sin;
		double newZ = this.zCoord * (double) cos - this.xCoord * (double) sin;
		this.xCoord = newX;
		this.zCoord = newZ;
	}
}

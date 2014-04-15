package net.rush.model.misc;


public class AxisAlignedBB {
	
	private static final ThreadLocal<?> theAABBLocalPool = new AABBLocalPool();
	
	public double minX;
	public double minY;
	public double minZ;
	public double maxX;
	public double maxY;
	public double maxZ;

	/**
	 * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
	 */
	public static AxisAlignedBB getBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
	}

	/**
	 * Gets the ThreadLocal AABBPool
	 */
	public static AABBPool getAABBPool() {
		return (AABBPool) theAABBLocalPool.get();
	}

	protected AxisAlignedBB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	/**
	 * Sets the bounds of the bounding box. Args: minX, minY, minZ, maxX, maxY, maxZ
	 */
	public AxisAlignedBB setBounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		return this;
	}

	/**
	 * Adds the coordinates to the bounding box extending it if the point lies outside the current ranges. Args: x, y, z
	 */
	public AxisAlignedBB addCoord(double x, double y, double z) {
		double minX = this.minX;
		double minY = this.minY;
		double minZ = this.minZ;
		double maxX = this.maxX;
		double maxY = this.maxY;
		double maxZ = this.maxZ;

		if (x < 0.0D) {
			minX += x;
		}

		if (x > 0.0D) {
			maxX += x;
		}

		if (y < 0.0D) {
			minY += y;
		}

		if (y > 0.0D) {
			maxY += y;
		}

		if (z < 0.0D) {
			minZ += z;
		}

		if (z > 0.0D) {
			maxZ += z;
		}

		return getAABBPool().getAABB(minX, minY, minZ, maxX, maxY, maxZ);
	}

	/**
	 * Returns a bounding box expanded by the specified vector (if negative numbers are given it will shrink). Args: x,
	 * y, z
	 */
	public AxisAlignedBB expand(double x, double y, double z) {
		double minX = this.minX - x;
		double minY = this.minY - y;
		double minZ = this.minZ - z;
		double maxX = this.maxX + x;
		double maxY = this.maxY + y;
		double maxZ = this.maxZ + z;
		return getAABBPool().getAABB(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public AxisAlignedBB func_111270_a(AxisAlignedBB par1AxisAlignedBB) {
		double newMinX = Math.min(this.minX, par1AxisAlignedBB.minX);
		double newMinY = Math.min(this.minY, par1AxisAlignedBB.minY);
		double newMinZ = Math.min(this.minZ, par1AxisAlignedBB.minZ);
		double newMaxX = Math.max(this.maxX, par1AxisAlignedBB.maxX);
		double newMaxY = Math.max(this.maxY, par1AxisAlignedBB.maxY);
		double newMaxZ = Math.max(this.maxZ, par1AxisAlignedBB.maxZ);
		return getAABBPool().getAABB(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
	}

	/**
	 * Returns a bounding box offseted by the specified vector (if negative numbers are given it will shrink). Args: x,
	 * y, z
	 */
	public AxisAlignedBB getOffsetBoundingBox(double x, double y, double z) {
		return getAABBPool().getAABB(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
	}

	/**
	 * if instance and the argument bounding boxes overlap in the Y and Z dimensions, calculate the offset between them
	 * in the X dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
	 * calculated offset.  Otherwise return the calculated offset.
	 */
	public double calculateXOffset(AxisAlignedBB par1AxisAlignedBB, double par2) {
		if (par1AxisAlignedBB.maxY > this.minY && par1AxisAlignedBB.minY < this.maxY) {
			if (par1AxisAlignedBB.maxZ > this.minZ && par1AxisAlignedBB.minZ < this.maxZ) {
				double var4;

				if (par2 > 0.0D && par1AxisAlignedBB.maxX <= this.minX) {
					var4 = this.minX - par1AxisAlignedBB.maxX;

					if (var4 < par2) {
						par2 = var4;
					}
				}

				if (par2 < 0.0D && par1AxisAlignedBB.minX >= this.maxX) {
					var4 = this.maxX - par1AxisAlignedBB.minX;

					if (var4 > par2) {
						par2 = var4;
					}
				}

				return par2;
			} else {
				return par2;
			}
		} else {
			return par2;
		}
	}

	/**
	 * if instance and the argument bounding boxes overlap in the X and Z dimensions, calculate the offset between them
	 * in the Y dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
	 * calculated offset.  Otherwise return the calculated offset.
	 */
	public double calculateYOffset(AxisAlignedBB par1AxisAlignedBB, double par2) {
		if (par1AxisAlignedBB.maxX > this.minX && par1AxisAlignedBB.minX < this.maxX) {
			if (par1AxisAlignedBB.maxZ > this.minZ && par1AxisAlignedBB.minZ < this.maxZ) {
				double var4;

				if (par2 > 0.0D && par1AxisAlignedBB.maxY <= this.minY) {
					var4 = this.minY - par1AxisAlignedBB.maxY;

					if (var4 < par2) {
						par2 = var4;
					}
				}

				if (par2 < 0.0D && par1AxisAlignedBB.minY >= this.maxY) {
					var4 = this.maxY - par1AxisAlignedBB.minY;

					if (var4 > par2) {
						par2 = var4;
					}
				}

				return par2;
			} else {
				return par2;
			}
		} else {
			return par2;
		}
	}

	/**
	 * if instance and the argument bounding boxes overlap in the Y and X dimensions, calculate the offset between them
	 * in the Z dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
	 * calculated offset.  Otherwise return the calculated offset.
	 */
	public double calculateZOffset(AxisAlignedBB par1AxisAlignedBB, double par2) {
		if (par1AxisAlignedBB.maxX > this.minX && par1AxisAlignedBB.minX < this.maxX) {
			if (par1AxisAlignedBB.maxY > this.minY && par1AxisAlignedBB.minY < this.maxY) {
				double var4;

				if (par2 > 0.0D && par1AxisAlignedBB.maxZ <= this.minZ) {
					var4 = this.minZ - par1AxisAlignedBB.maxZ;

					if (var4 < par2) {
						par2 = var4;
					}
				}

				if (par2 < 0.0D && par1AxisAlignedBB.minZ >= this.maxZ) {
					var4 = this.maxZ - par1AxisAlignedBB.minZ;

					if (var4 > par2) {
						par2 = var4;
					}
				}

				return par2;
			} else {
				return par2;
			}
		} else {
			return par2;
		}
	}

	/**
	 * Returns whether the given bounding box intersects with this one. Args: axisAlignedBB
	 */
	public boolean intersectsWith(AxisAlignedBB par1AxisAlignedBB) {
		return par1AxisAlignedBB.maxX > this.minX && par1AxisAlignedBB.minX < this.maxX ? (par1AxisAlignedBB.maxY > this.minY && par1AxisAlignedBB.minY < this.maxY ? par1AxisAlignedBB.maxZ > this.minZ && par1AxisAlignedBB.minZ < this.maxZ
				: false) : false;
	}

	/**
	 * Offsets the current bounding box by the specified coordinates. Args: x, y, z
	 */
	public AxisAlignedBB offset(double par1, double par3, double par5) {
		this.minX += par1;
		this.minY += par3;
		this.minZ += par5;
		this.maxX += par1;
		this.maxY += par3;
		this.maxZ += par5;
		return this;
	}

	/**
	 * Returns if the supplied Vec3D is completely inside the bounding box
	 */
	public boolean isVecInside(Vec3 par1Vec3) {
		return par1Vec3.xCoord > this.minX && par1Vec3.xCoord < this.maxX ? (par1Vec3.yCoord > this.minY && par1Vec3.yCoord < this.maxY ? par1Vec3.zCoord > this.minZ && par1Vec3.zCoord < this.maxZ : false) : false;
	}

	/**
	 * Returns the average length of the edges of the bounding box.
	 */
	public double getAverageEdgeLength() {
		double var1 = this.maxX - this.minX;
		double var3 = this.maxY - this.minY;
		double var5 = this.maxZ - this.minZ;
		return (var1 + var3 + var5) / 3.0D;
	}

	/**
	 * Returns a bounding box that is inset by the specified amounts
	 */
	public AxisAlignedBB contract(double par1, double par3, double par5) {
		double var7 = this.minX + par1;
		double var9 = this.minY + par3;
		double var11 = this.minZ + par5;
		double var13 = this.maxX - par1;
		double var15 = this.maxY - par3;
		double var17 = this.maxZ - par5;
		return getAABBPool().getAABB(var7, var9, var11, var13, var15, var17);
	}

	/**
	 * Returns a copy of the bounding box.
	 */
	public AxisAlignedBB copy() {
		return getAABBPool().getAABB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
	}

	public MovingObjectPosition calculateIntercept(Vec3 par1Vec3, Vec3 par2Vec3) {
		Vec3 var3 = par1Vec3.getIntermediateWithXValue(par2Vec3, this.minX);
		Vec3 var4 = par1Vec3.getIntermediateWithXValue(par2Vec3, this.maxX);
		Vec3 var5 = par1Vec3.getIntermediateWithYValue(par2Vec3, this.minY);
		Vec3 var6 = par1Vec3.getIntermediateWithYValue(par2Vec3, this.maxY);
		Vec3 var7 = par1Vec3.getIntermediateWithZValue(par2Vec3, this.minZ);
		Vec3 var8 = par1Vec3.getIntermediateWithZValue(par2Vec3, this.maxZ);

		if (!this.isVecInYZ(var3)) {
			var3 = null;
		}

		if (!this.isVecInYZ(var4)) {
			var4 = null;
		}

		if (!this.isVecInXZ(var5)) {
			var5 = null;
		}

		if (!this.isVecInXZ(var6)) {
			var6 = null;
		}

		if (!this.isVecInXY(var7)) {
			var7 = null;
		}

		if (!this.isVecInXY(var8)) {
			var8 = null;
		}

		Vec3 var9 = null;

		if (var3 != null && (var9 == null || par1Vec3.squareDistanceTo(var3) < par1Vec3.squareDistanceTo(var9))) {
			var9 = var3;
		}

		if (var4 != null && (var9 == null || par1Vec3.squareDistanceTo(var4) < par1Vec3.squareDistanceTo(var9))) {
			var9 = var4;
		}

		if (var5 != null && (var9 == null || par1Vec3.squareDistanceTo(var5) < par1Vec3.squareDistanceTo(var9))) {
			var9 = var5;
		}

		if (var6 != null && (var9 == null || par1Vec3.squareDistanceTo(var6) < par1Vec3.squareDistanceTo(var9))) {
			var9 = var6;
		}

		if (var7 != null && (var9 == null || par1Vec3.squareDistanceTo(var7) < par1Vec3.squareDistanceTo(var9))) {
			var9 = var7;
		}

		if (var8 != null && (var9 == null || par1Vec3.squareDistanceTo(var8) < par1Vec3.squareDistanceTo(var9))) {
			var9 = var8;
		}

		if (var9 == null) {
			return null;
		} else {
			byte var10 = -1;

			if (var9 == var3) {
				var10 = 4;
			}

			if (var9 == var4) {
				var10 = 5;
			}

			if (var9 == var5) {
				var10 = 0;
			}

			if (var9 == var6) {
				var10 = 1;
			}

			if (var9 == var7) {
				var10 = 2;
			}

			if (var9 == var8) {
				var10 = 3;
			}

			return new MovingObjectPosition(0, 0, 0, var10, var9);
		}
	}

	/**
	 * Checks if the specified vector is within the YZ dimensions of the bounding box. Args: Vec3D
	 */
	private boolean isVecInYZ(Vec3 par1Vec3) {
		return par1Vec3 == null ? false : par1Vec3.yCoord >= this.minY && par1Vec3.yCoord <= this.maxY && par1Vec3.zCoord >= this.minZ && par1Vec3.zCoord <= this.maxZ;
	}

	/**
	 * Checks if the specified vector is within the XZ dimensions of the bounding box. Args: Vec3D
	 */
	private boolean isVecInXZ(Vec3 par1Vec3) {
		return par1Vec3 == null ? false : par1Vec3.xCoord >= this.minX && par1Vec3.xCoord <= this.maxX && par1Vec3.zCoord >= this.minZ && par1Vec3.zCoord <= this.maxZ;
	}

	/**
	 * Checks if the specified vector is within the XY dimensions of the bounding box. Args: Vec3D
	 */
	private boolean isVecInXY(Vec3 par1Vec3) {
		return par1Vec3 == null ? false : par1Vec3.xCoord >= this.minX && par1Vec3.xCoord <= this.maxX && par1Vec3.yCoord >= this.minY && par1Vec3.yCoord <= this.maxY;
	}

	/**
	 * Sets the bounding box to the same bounds as the bounding box passed in. Args: axisAlignedBB
	 */
	public void setBB(AxisAlignedBB par1AxisAlignedBB) {
		this.minX = par1AxisAlignedBB.minX;
		this.minY = par1AxisAlignedBB.minY;
		this.minZ = par1AxisAlignedBB.minZ;
		this.maxX = par1AxisAlignedBB.maxX;
		this.maxY = par1AxisAlignedBB.maxY;
		this.maxZ = par1AxisAlignedBB.maxZ;
	}

	public String toString() {
		return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
	}

	final static class AABBLocalPool extends ThreadLocal<Object> {
		protected AABBPool createNewDefaultPool() {
			return new AABBPool(300, 2000);
		}

		protected Object initialValue() {
			return this.createNewDefaultPool();
		}
	}

}

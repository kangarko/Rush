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

	public void setBounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	/**
	 * Adds the coordinates to the bounding box extending it if the point lies outside the current ranges.
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
	 * Returns a bounding box expanded by the specified vector (if negative numbers are given it will shrink)
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
	 * Returns a bounding box offseted by the specified vector (if negative numbers are given it will shrink).
	 */
	public AxisAlignedBB getOffsetBoundingBox(double x, double y, double z) {
		return getAABBPool().getAABB(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
	}

	/**
	 * if instance and the argument bounding boxes overlap in the Y and Z dimensions, calculate the offset between them
	 * in the X dimension.  
	 * @return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
	 * calculated offset.  Otherwise return the calculated offset.
	 */
	public double calculateXOffset(AxisAlignedBB axis, double offSet) {
		if (axis.maxY > this.minY && axis.minY < this.maxY) {
			if (axis.maxZ > this.minZ && axis.minZ < this.maxZ) {
				double var4;

				if (offSet > 0.0D && axis.maxX <= this.minX) {
					var4 = this.minX - axis.maxX;

					if (var4 < offSet) {
						offSet = var4;
					}
				}

				if (offSet < 0.0D && axis.minX >= this.maxX) {
					var4 = this.maxX - axis.minX;

					if (var4 > offSet) {
						offSet = var4;
					}
				}

				return offSet;
			} else {
				return offSet;
			}
		} else {
			return offSet;
		}
	}

	/**
	 * if instance and the argument bounding boxes overlap in the X and Z dimensions, calculate the offset between them
	 * in the Y dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
	 * calculated offset.  Otherwise return the calculated offset.
	 */
	public double calculateYOffset(AxisAlignedBB axis, double offset) {
		if (axis.maxX > this.minX && axis.minX < this.maxX) {
			if (axis.maxZ > this.minZ && axis.minZ < this.maxZ) {
				double var4;

				if (offset > 0.0D && axis.maxY <= this.minY) {
					var4 = this.minY - axis.maxY;

					if (var4 < offset) {
						offset = var4;
					}
				}

				if (offset < 0.0D && axis.minY >= this.maxY) {
					var4 = this.maxY - axis.minY;

					if (var4 > offset) {
						offset = var4;
					}
				}

				return offset;
			} else {
				return offset;
			}
		} else {
			return offset;
		}
	}

	/**
	 * if instance and the argument bounding boxes overlap in the Y and X dimensions, calculate the offset between them
	 * in the Z dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
	 * calculated offset.  Otherwise return the calculated offset.
	 */
	public double calculateZOffset(AxisAlignedBB axis, double offset) {
		if (axis.maxX > this.minX && axis.minX < this.maxX) {
			if (axis.maxY > this.minY && axis.minY < this.maxY) {
				double var4;

				if (offset > 0.0D && axis.maxZ <= this.minZ) {
					var4 = this.minZ - axis.maxZ;

					if (var4 < offset) {
						offset = var4;
					}
				}

				if (offset < 0.0D && axis.minZ >= this.maxZ) {
					var4 = this.maxZ - axis.minZ;

					if (var4 > offset) {
						offset = var4;
					}
				}

				return offset;
			} else {
				return offset;
			}
		} else {
			return offset;
		}
	}

	/**
	 * Returns whether the given bounding box intersects with this one. Args: axisAlignedBB
	 */
	public boolean intersectsWith(AxisAlignedBB axis) {
		return axis.maxX > this.minX && axis.minX < this.maxX ? (axis.maxY > this.minY && axis.minY < this.maxY ? axis.maxZ > this.minZ && axis.minZ < this.maxZ : false) : false;
	}

	/**
	 * Offsets the current bounding box by the specified coordinates. Args: x, y, z
	 */
	public AxisAlignedBB offset(double x, double y, double z) {
		this.minX += x;
		this.minY += y;
		this.minZ += z;
		this.maxX += x;
		this.maxY += y;
		this.maxZ += z;
		return this;
	}

	/**
	 * Returns if the supplied Vec3D is completely inside the bounding box
	 */
	public boolean isVecInside(Vec3 vec) {
		return vec.xCoord > this.minX && vec.xCoord < this.maxX ? (vec.yCoord > this.minY && vec.yCoord < this.maxY ? vec.zCoord > this.minZ && vec.zCoord < this.maxZ : false) : false;
	}

	/**
	 * Returns the average length of the edges of the bounding box.
	 */
	public double getAverageEdgeLength() {
		double x = this.maxX - this.minX;
		double y = this.maxY - this.minY;
		double z = this.maxZ - this.minZ;
		return (x + y + z) / 3.0D;
	}

	/**
	 * Returns a bounding box that is inset by the specified amounts
	 */
	public AxisAlignedBB contract(double x, double y, double z) {
		double var7 = this.minX + x;
		double var9 = this.minY + y;
		double var11 = this.minZ + z;
		double var13 = this.maxX - x;
		double var15 = this.maxY - y;
		double var17 = this.maxZ - z;
		return getAABBPool().getAABB(var7, var9, var11, var13, var15, var17);
	}

	/**
	 * Returns a copy of the bounding box.
	 */
	public AxisAlignedBB copy() {
		return getAABBPool().getAABB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
	}

	public MovingObjectPosition calculateIntercept(Vec3 vec1, Vec3 vec2) {
		Vec3 var3 = vec1.getIntermediateWithXValue(vec2, this.minX);
		Vec3 var4 = vec1.getIntermediateWithXValue(vec2, this.maxX);
		Vec3 var5 = vec1.getIntermediateWithYValue(vec2, this.minY);
		Vec3 var6 = vec1.getIntermediateWithYValue(vec2, this.maxY);
		Vec3 var7 = vec1.getIntermediateWithZValue(vec2, this.minZ);
		Vec3 var8 = vec1.getIntermediateWithZValue(vec2, this.maxZ);

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

		Vec3 vector = null;

		if (var3 != null && (vector == null || vec1.squareDistanceTo(var3) < vec1.squareDistanceTo(vector))) {
			vector = var3;
		}

		if (var4 != null && (vector == null || vec1.squareDistanceTo(var4) < vec1.squareDistanceTo(vector))) {
			vector = var4;
		}

		if (var5 != null && (vector == null || vec1.squareDistanceTo(var5) < vec1.squareDistanceTo(vector))) {
			vector = var5;
		}

		if (var6 != null && (vector == null || vec1.squareDistanceTo(var6) < vec1.squareDistanceTo(vector))) {
			vector = var6;
		}

		if (var7 != null && (vector == null || vec1.squareDistanceTo(var7) < vec1.squareDistanceTo(vector))) {
			vector = var7;
		}

		if (var8 != null && (vector == null || vec1.squareDistanceTo(var8) < vec1.squareDistanceTo(vector))) {
			vector = var8;
		}

		if (vector == null) {
			return null;
		} else {
			byte var10 = -1;

			if (vector == var3) {
				var10 = 4;
			}

			if (vector == var4) {
				var10 = 5;
			}

			if (vector == var5) {
				var10 = 0;
			}

			if (vector == var6) {
				var10 = 1;
			}

			if (vector == var7) {
				var10 = 2;
			}

			if (vector == var8) {
				var10 = 3;
			}

			return new MovingObjectPosition(0, 0, 0, var10, vector);
		}
	}

	/**
	 * Checks if the specified vector is within the YZ dimensions of the bounding box.
	 */
	private boolean isVecInYZ(Vec3 vec) {
		return vec == null ? false : vec.yCoord >= this.minY && vec.yCoord <= this.maxY && vec.zCoord >= this.minZ && vec.zCoord <= this.maxZ;
	}

	/**
	 * Checks if the specified vector is within the XZ dimensions of the bounding box. Args: Vec3D
	 */
	private boolean isVecInXZ(Vec3 vec) {
		return vec == null ? false : vec.xCoord >= this.minX && vec.xCoord <= this.maxX && vec.zCoord >= this.minZ && vec.zCoord <= this.maxZ;
	}

	/**
	 * Checks if the specified vector is within the XY dimensions of the bounding box. Args: Vec3D
	 */
	private boolean isVecInXY(Vec3 vec) {
		return vec == null ? false : vec.xCoord >= this.minX && vec.xCoord <= this.maxX && vec.yCoord >= this.minY && vec.yCoord <= this.maxY;
	}

	/**
	 * Sets the bounding box to the same bounds as the bounding box passed in. Args: axisAlignedBB
	 */
	public void setBB(AxisAlignedBB axis) {
		this.minX = axis.minX;
		this.minY = axis.minY;
		this.minZ = axis.minZ;
		this.maxX = axis.maxX;
		this.maxY = axis.maxY;
		this.maxZ = axis.maxZ;
	}
	
	@Override
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

package net.rush.model.misc;

import java.util.ArrayList;
import java.util.List;

public class Vec3Pool {
	
	private final int truncateResetThreshold;
	private final int minSize;

	/** items at and above nextFreeSpace are assumed to be available */
	private final List<Vec3> vec3Cache = new ArrayList<Vec3>();
	private int nextFreeSpace;
	private int maxSizeSinceLastTruncation;
	private int resetCount;

	public Vec3Pool(int truncateResetThreshold, int minSize) {
		this.truncateResetThreshold = truncateResetThreshold;
		this.minSize = minSize;
	}

	/**
	 * extends the pool if all vecs are currently "out"
	 */
	public Vec3 getVecFromPool(double x, double y, double z) {
		if (isEmpty()) {
			return new Vec3(this, x, y, z);
		} else {
			Vec3 vector;

			if (nextFreeSpace >= vec3Cache.size()) {
				vector = new Vec3(this, x, y, z);
				vec3Cache.add(vector);
			} else {
				vector = (Vec3) vec3Cache.get(nextFreeSpace);
				vector.setComponents(x, y, z);
			}

			++nextFreeSpace;
			return vector;
		}
	}

	/**
	 * Will truncate the array everyN clears to the maximum size observed since the last truncation.
	 */
	public void clear() {
		if (!isEmpty()) {
			if (nextFreeSpace > maxSizeSinceLastTruncation) {
				maxSizeSinceLastTruncation = nextFreeSpace;
			}

			if (resetCount++ == truncateResetThreshold) {
				int var1 = Math.max(maxSizeSinceLastTruncation, vec3Cache.size() - minSize);

				while (vec3Cache.size() > var1) {
					vec3Cache.remove(var1);
				}

				maxSizeSinceLastTruncation = 0;
				resetCount = 0;
			}

			nextFreeSpace = 0;
		}
	}

	public int getPoolSize() {
		return vec3Cache.size();
	}

	public int getNextFreeSpace() {
		return nextFreeSpace;
	}

	private boolean isEmpty() {
		return minSize < 0 || truncateResetThreshold < 0;
	}
}

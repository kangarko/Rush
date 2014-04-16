package net.rush.model.misc;

import java.util.ArrayList;
import java.util.List;

public class AABBPool {
	
	/**
	 * Maximum number of times the pool can be "cleaned" before the list is shrunk
	 */
	private final int maxNumCleans;

	/**
	 * Number of Pool entries to remove when cleanPool is called maxNumCleans times.
	 */
	private final int numEntriesToRemove;

	/** List of AABB stored in this Pool */
	private final List<AxisAlignedBB> listAABB = new ArrayList<AxisAlignedBB>();

	/** Next index to use when adding a Pool Entry. */
	private int nextPoolIndex;

	/**
	 * Largest index reached by this Pool (can be reset to 0 upon calling cleanPool)
	 */
	private int maxPoolIndex;

	/** Number of times this Pool has been cleaned */
	private int numCleans;

	public AABBPool(int maxNumCleans, int numEntriesToRemove) {
		this.maxNumCleans = maxNumCleans;
		this.numEntriesToRemove = numEntriesToRemove;
	}

	/**
	 * Creates a new AABB, or reuses one that's no longer in use. Parameters: minX, minY, minZ, maxX, maxY, maxZ. AABBs
	 * returned from this function should only be used for one frame or tick, as after that they will be reused.
	 */
	public AxisAlignedBB getAABB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		AxisAlignedBB axis;

		if (this.nextPoolIndex >= this.listAABB.size()) {
			axis = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
			this.listAABB.add(axis);
		} else {
			axis = (AxisAlignedBB) this.listAABB.get(this.nextPoolIndex);
			axis.setBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}

		++this.nextPoolIndex;
		return axis;
	}

	/**
	 * Marks the pool as "empty", starting over when adding new entries. If this is called maxNumCleans times, the list
	 * size is reduced
	 */
	public void cleanPool() {
		if (this.nextPoolIndex > this.maxPoolIndex) {
			this.maxPoolIndex = this.nextPoolIndex;
		}

		if (this.numCleans++ == this.maxNumCleans) {
			int entry = Math.max(this.maxPoolIndex, this.listAABB.size() - this.numEntriesToRemove);

			while (this.listAABB.size() > entry) {
				this.listAABB.remove(entry);
			}

			this.maxPoolIndex = 0;
			this.numCleans = 0;
		}

		this.nextPoolIndex = 0;
	}

	public int getlistAABBsize() {
		return this.listAABB.size();
	}

	public int getnextPoolIndex() {
		return this.nextPoolIndex;
	}
}

package net.rush.model.misc;

import net.rush.model.Block;

public class NextTickListEntry implements Comparable<Object> {
	/** The id number for the next tick entry */
	private static long nextTickEntryID;

	/** X position this tick is occuring at */
	public int xCoord;

	/** Y position this tick is occuring at */
	public int yCoord;

	/** Z position this tick is occuring at */
	public int zCoord;

	/**
	 * blockID of the scheduled tick (ensures when the tick occurs its still for this block)
	 */
	public int blockID;

	/** Time this tick is scheduled to occur at */
	public long scheduledTime;
	public int priority;

	/** The id of the tick entry */
	private long tickEntryID;

	public NextTickListEntry(int par1, int par2, int par3, int par4) {
		tickEntryID = (nextTickEntryID++);
		xCoord = par1;
		yCoord = par2;
		zCoord = par3;
		blockID = par4;
	}

	public boolean equals(Object par1Obj) {
		if (!(par1Obj instanceof NextTickListEntry))
			return false;
		else {
			NextTickListEntry var2 = (NextTickListEntry) par1Obj;
			return xCoord == var2.xCoord && yCoord == var2.yCoord && zCoord == var2.zCoord && Block.isAssociatedWith(blockID, var2.blockID);
		}
	}

	public int hashCode() {
		return (xCoord * 1024 * 1024 + zCoord * 1024 + yCoord) * 256;
	}

	/**
	 * Sets the scheduled time for this tick entry
	 */
	public NextTickListEntry setScheduledTime(long scheduledTime) {
		this.scheduledTime = scheduledTime;
		return this;
	}

	public void setPriority(int par1) {
		priority = par1;
	}

	/**
	 * Compares this tick entry to another tick entry for sorting purposes. Compared first based on the scheduled time
	 * and second based on tickEntryID.
	 */
	public int comparer(NextTickListEntry nextTickEntry) {
		return scheduledTime < nextTickEntry.scheduledTime ? -1 : (scheduledTime > nextTickEntry.scheduledTime ? 1 : (priority != nextTickEntry.priority ? priority - nextTickEntry.priority
				: (tickEntryID < nextTickEntry.tickEntryID ? -1 : (tickEntryID > nextTickEntry.tickEntryID ? 1 : 0))));
	}

	public String toString() {
		return blockID + ": (" + xCoord + ", " + yCoord + ", " + zCoord + "), " + scheduledTime + ", " + priority + ", " + tickEntryID;
	}

	public int compareTo(Object obj) {
		return comparer((NextTickListEntry) obj);
	}
}

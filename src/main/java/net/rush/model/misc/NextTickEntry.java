package net.rush.model.misc;

import net.rush.model.Block;

public class NextTickEntry implements Comparable<Object> {
	/** The id number for the next tick entry */
	private static long nextTickEntryId;

	/** X position this tick is occuring at */
	public int posX;

	/** Y position this tick is occuring at */
	public int posY;

	/** Z position this tick is occuring at */
	public int posZ;

	/**
	 * blockID of the scheduled tick (ensures when the tick occurs its still for this block)
	 */
	public int blockId;

	/** Time this tick is scheduled to occur at */
	public long scheduledTime;
	public int priority;

	/** The id of the tick entry */
	private long tickEntryID;

	public NextTickEntry(int x, int y, int z, int blockId) {
		tickEntryID = (nextTickEntryId++);
		posX = x;
		posY = y;
		posZ = z;
		this.blockId = blockId;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof NextTickEntry))
			return false;
		else {
			NextTickEntry entry = (NextTickEntry) obj;
			return posX == entry.posX && posY == entry.posY && posZ == entry.posZ && Block.isAssociatedWith(blockId, entry.blockId);
		}
	}

	public int hashCode() {
		return (posX * 1024 * 1024 + posZ * 1024 + posY) * 256;
	}

	/**
	 * Sets the scheduled time for this tick entry
	 */
	public NextTickEntry setScheduledTime(long scheduledTime) {
		this.scheduledTime = scheduledTime;
		return this;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * Compares this tick entry to another tick entry for sorting purposes. Compared first based on the scheduled time
	 * and second based on tickEntryID.
	 */
	public int comparer(NextTickEntry entry) {
		return scheduledTime < entry.scheduledTime ? -1 : (scheduledTime > entry.scheduledTime ? 1 : (priority != entry.priority ? priority - entry.priority
				: (tickEntryID < entry.tickEntryID ? -1 : (tickEntryID > entry.tickEntryID ? 1 : 0))));
	}

	public String toString() {
		return blockId + ": (" + posX + ", " + posY + ", " + posZ + "), " + scheduledTime + ", " + priority + ", " + tickEntryID;
	}

	public int compareTo(Object entry) {
		return comparer((NextTickEntry) entry);
	}
}

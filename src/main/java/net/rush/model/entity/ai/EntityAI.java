package net.rush.model.entity.ai;

public abstract class EntityAI {

	public abstract boolean shouldExecute();

	/**
	 * Returns whether the task requires multiple updates or not
	 */
	public boolean isContinuous() {
		return true;
	}

	public void pulse() {
	}
}

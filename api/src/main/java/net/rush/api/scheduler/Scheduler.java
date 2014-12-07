package net.rush.api.scheduler;

public interface Scheduler {

	/**
	 * Runs provided task synchronously.
	 * @param task the task
	 */
	public void runTask(Runnable task);
}

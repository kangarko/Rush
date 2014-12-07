package net.rush.api;

import java.util.logging.Logger;

import net.rush.api.scheduler.Scheduler;

public interface Server {

	/**
	 * @returns Scheduler that can perform sync, async and delayed tasks.
	 */
	public Scheduler getScheduler();
	
	
	/**
	 * @returns Logger that prints formatted messages in the console.
	 */
	public Logger getLogger();
}

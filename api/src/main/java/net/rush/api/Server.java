package net.rush.api;

import java.util.logging.Logger;

import net.rush.api.model.ConsoleCommandSender;
import net.rush.api.scheduler.Scheduler;

public interface Server {

	/**
	 * Scheduler can perform sync, async and delayed tasks.
	 * @returns server's scheduler
	 */
	public Scheduler getScheduler();
	
	
	/**
	 * Logger that prints formatted messages in the console.
	 * @returns server's logger
	 */
	public Logger getLogger();
	
	/**
	 * Return the server console that can execute commands.
	 * @returns server's console command sender
	 */
	public ConsoleCommandSender getConsoleCommandSender();
	
	/**
	 * Stops the server.
	 */
	public void stop();
}

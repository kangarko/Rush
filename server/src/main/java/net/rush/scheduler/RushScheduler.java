package net.rush.scheduler;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

import net.rush.RushServer;
import net.rush.api.scheduler.Scheduler;

public class RushScheduler implements Scheduler {

	private final Timer pulseTask = new Timer("Pulse");
	private final Queue<Runnable> pendingTasks = new LinkedList<>();
	private final RushServer server;

	public RushScheduler(RushServer server) {
		this.server = server;
	}

	public void init() {
		pulseTask.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				long now = System.currentTimeMillis();
				
				try {
					pulse();
				} catch (Throwable t) {
					server.getLogger().log(Level.SEVERE, "Uncaught exception in scheduler", t);
				} finally {
					long lag = System.currentTimeMillis() - now;
					if (lag > 99)
						System.out.println("[Lag] Server froze for " + lag + " miliseconds.");
				}
			}
		}, 0, 50);
	}

	public void pulse() {
		// Pull all pending tasks.
		synchronized (pendingTasks) {
			Runnable pending;

			while ((pending = pendingTasks.poll()) != null)
				pending.run();
		}

		// Pulse each connection and handle it.
		server.sessionRegistry.pulse();

		// Handle general game logic.
		server.world.pulse();
	}

	@Override
	public void runTask(Runnable task) {
		synchronized (pendingTasks) {
			pendingTasks.add(task);	
		}
	}
}

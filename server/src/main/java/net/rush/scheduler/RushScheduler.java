package net.rush.scheduler;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.rush.RushServer;
import net.rush.api.scheduler.Scheduler;

public class RushScheduler implements Scheduler {

	private final ScheduledExecutorService executors = Executors.newSingleThreadScheduledExecutor();
	private final RushServer server;

	private Queue<Runnable> pendingTasks = new LinkedList<>();

	public RushScheduler(RushServer server) {
		this.server = server;
	}

	public void init() {
		executors.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				pulse();
			}
		}, 0, 50, TimeUnit.MILLISECONDS);
	}

	public void pulse() {
		// Pull all pending tasks.
		synchronized (pendingTasks) {
			Runnable pending;

			while ((pending = pendingTasks.poll()) != null)
				pending.run();
		}
		
		// Pulse connections.
		server.sessionRegistry.pulse();
	}

	@Override
	public void runTask(Runnable task) {
		synchronized (pendingTasks) {
			pendingTasks.add(task);	
		}
	}
}

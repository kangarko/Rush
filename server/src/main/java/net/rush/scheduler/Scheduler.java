package net.rush.scheduler;

import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {

	private final Queue<Runnable> pendingTasks = new LinkedList<>();

	public void pulse() {
		synchronized (pendingTasks) {
			Runnable pending;

			while ((pending = pendingTasks.poll()) != null)
				pending.run();
		}
	}
	
	public void runTask(Runnable task) {
		synchronized (pendingTasks) {
			pendingTasks.add(task);	
		}
	}
}

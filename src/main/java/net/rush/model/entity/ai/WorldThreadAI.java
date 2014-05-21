package net.rush.model.entity.ai;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class WorldThreadAI {

	private static ExecutorService executor = Executors.newFixedThreadPool(1, new ThreadFactoryBuilder().setNameFormat("Entity AI IO").build());

	public static void addTask(final EntityAI task) {
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				task.pulse();
			}
		});
	}

}

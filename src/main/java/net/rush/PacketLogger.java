package net.rush;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PacketLogger {

	private static ExecutorService executor = Executors.newWorkStealingPool();


	public static void submitWrite(final Object str, final boolean read) {
		executor.submit(new Runnable() {
			@Override
			public void run() {
				try {
					write(str, read);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void write(Object str, boolean read) throws IOException {		
		BufferedWriter bw = null;
		File file = new File("packets-dump.log");
		if(!file.exists())
			file.createNewFile();
		
		try {
			bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(getTime() + (read ? "Reading " : "Writing ") + ": " + str);
			bw.newLine();
		} finally {
			if (bw != null) {
				bw.flush();
				bw.close();
			}
		}

	}

	private static String getTime() {
		DateFormat date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		return "[" + date.format(System.currentTimeMillis()) + "] ";
	}
}

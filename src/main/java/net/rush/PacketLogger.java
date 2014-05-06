package net.rush;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.rush.packets.Packet;

public class PacketLogger {

	private static ExecutorService executor = Executors.newSingleThreadExecutor();
	private static List<String> ignored = Arrays.asList("MapChunkPacket", "BlockChangePacket");
	
	private static File file = new File("packets-dump.log");

	public static void submitWrite(final Packet packet, final int protocol, final boolean read) {
		executor.submit(new Runnable() {
			@Override
			public void run() {
				try {
					write(packet, protocol, read);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void write(Packet packet, int protocol, boolean read) throws Exception {
		if(ignored.contains(packet.getPacketType().getSimpleName()))
			return;
		// Prevent too big file
		if((file.length() / 1000) > 200)
			file.renameTo(new File("old_packet.log"));
			
		FileWriter fw = null;
		if(!file.exists())
			file.createNewFile();
		
		try {
			fw = new FileWriter(file, true);
			fw.write(getTime() + (read ? "Read  " : "Write ") + "(p=" + protocol + "): " + packet);
			fw.write(System.lineSeparator());
		} finally {
			if (fw != null) {
				fw.flush();
				fw.close();
			}
		}

	}

	private static String getTime() {
		DateFormat date = new SimpleDateFormat("HH:mm:ss");
		return "[" + date.format(System.currentTimeMillis()) + "] ";
	}
}

package net.rush;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.rush.packets.Packet;

public class PacketLogger {

	private static ExecutorService executor = Executors.newWorkStealingPool();
	private static List<String> ignored = Arrays.asList("MapChunkPacket", "BlockChangePacket");

	public static void submitWrite(final Packet packet, final int protocol, final boolean read) {
		executor.submit(new Runnable() {
			@Override
			public void run() {
				try {
					write(packet, protocol, read);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void write(Packet packet, int protocol, boolean read) throws IOException {
		if(ignored.contains(packet.getPacketType().getSimpleName()))
			return;
		
		BufferedWriter bw = null;
		File file = new File("packets-dump.log");
		if(!file.exists())
			file.createNewFile();
		
		try {
			bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(getTime() + (read ? "Reading " : "Writing ") + " (prot=" + protocol + "): " + packet);
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

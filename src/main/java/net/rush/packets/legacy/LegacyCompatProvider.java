package net.rush.packets.legacy;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LegacyCompatProvider {

	private static List<String> compats = Collections.synchronizedList(new ArrayList<String>(100));

	public static void provideCompatFor(SocketAddress address) {
		ensure(address);
		InetSocketAddress isa = (InetSocketAddress) address;
		compats.add(isa.getHostString());
	}

	public static boolean isProvidingCompat(SocketAddress address) {
		ensure(address);
		InetSocketAddress isa = (InetSocketAddress) address;
		return compats.contains(isa.getHostString());
	}

	public static void stopProvidingCompatFor(SocketAddress address) {
		ensure(address);
		InetSocketAddress isa = (InetSocketAddress) address;
		compats.remove(isa.getHostString());
	}

	private static void ensure(SocketAddress address) {
		if (compats.size() > 100)
			compats.remove(0);
		
		if (!(address instanceof InetSocketAddress)) 
			throw new IllegalArgumentException("Unexpected SocketAddress! :" + address);

	}
}

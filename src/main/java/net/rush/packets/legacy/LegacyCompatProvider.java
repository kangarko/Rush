package net.rush.packets.legacy;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LegacyCompatProvider {

	private static List<String> compats = Collections.synchronizedList(new ArrayList<String>(100));
	private static List<String> throttled = Collections.synchronizedList(new ArrayList<String>(100));

	public static void throttle(SocketAddress address) {
		check(address);
		InetSocketAddress isa = (InetSocketAddress) address;
		throttled.add(isa.getHostString());
	}
	
	public static boolean isThrottled(SocketAddress address) {
		check(address);
		InetSocketAddress isa = (InetSocketAddress) address;
		return throttled.contains(isa.getHostString());
	}
	
	private static void unThrottle(String addr) {
		throttled.remove(addr);
	}
	
	public static void provideCompatFor(SocketAddress address) {
		check(address);
		InetSocketAddress isa = (InetSocketAddress) address;
		compats.add(isa.getHostString());
	}

	public static boolean isProvidingCompat(SocketAddress address) {
		check(address);
		InetSocketAddress isa = (InetSocketAddress) address;
		return compats.contains(isa.getHostString());
	}

	public static void stopProvidingCompatFor(SocketAddress address) {
		check(address);
		InetSocketAddress isa = (InetSocketAddress) address;
		compats.remove(isa.getHostString());
		unThrottle(isa.getHostString());
	}

	private static void check(SocketAddress address) {
		if (compats.size() > 100)
			compats.remove(0);
		
		if (!(address instanceof InetSocketAddress)) 
			throw new IllegalArgumentException("Unexpected SocketAddress! :" + address);
	}
}

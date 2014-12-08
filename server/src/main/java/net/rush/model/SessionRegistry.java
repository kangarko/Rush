package net.rush.model;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;

import net.rush.protocol.Packet;

public final class SessionRegistry {

	private final Queue<Session> pending = new ArrayDeque<>();
	public final HashSet<Session> sessions = new HashSet<>();

	public void pulse() {
		synchronized (pending) {
			Session session;
			
			while ((session = pending.poll()) != null)
				sessions.add(session);
		}

		for (Iterator<Session> it = sessions.iterator(); it.hasNext(); ) {
			Session session = it.next();

			if (session.isPendingRemoval())
				it.remove();
			else
				session.pulse();
		}
	}
	
	public void broadcastPacket(Packet packet) {
		for (Session session : sessions)
			session.sendPacket(packet);
	}

	public void add(Session session) {
		synchronized (pending) {
			pending.add(session);
		}
	}

	public void remove(Session session) {
		session.destroy();
	}

}


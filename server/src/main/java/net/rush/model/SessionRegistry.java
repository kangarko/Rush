package net.rush.model;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;

public final class SessionRegistry {

	private final Queue<Session> pending = new ArrayDeque<>();
	private final HashSet<Session> sessions = new HashSet<>();

	public void pulse() {
		synchronized (pending) {
			Session session;
			
			while ((session = pending.poll()) != null)
				sessions.add(session);
		}

		for (Iterator<Session> it = sessions.iterator(); it.hasNext(); ) {
			Session session = it.next();

			if (session.active)
				session.pulse();
			else
				it.remove();
		}
	}

	public void add(Session session) {
		synchronized (pending) {
			pending.add(session);
		}
	}

	public void remove(Session session) {
		session.active = false;
	}

}


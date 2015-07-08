package net.rush.model;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import org.apache.commons.lang3.Validate;

import net.rush.api.safety.SafeUnorderedZoznam;
import net.rush.entity.EntityPlayer;
import net.rush.protocol.Packet;

public final class SessionRegistry {

	private final Queue<Session> pending = new ArrayDeque<>();
	private final SafeUnorderedZoznam<Session> sessions = new SafeUnorderedZoznam<>();

	public void pulse() {
		synchronized (pending) {
			Session session;

			while ((session = pending.poll()) != null)
				sessions.add(session);
		}

		for (Iterator<Session> it = sessions.iterator(); it.hasNext(); ) {
			Session session = it.next();

			if (session.isPendingRemoval()) {
				session.onDispose();
				it.remove();
			} else
				session.pulse();
		}
	}
	
	public void broadcastPacketExcept(Packet packet, int entityId) {
		for (Session session : sessions) {
			if (session.hasPlayer() && session.getPlayer().getId() == entityId)
				continue;
			
			session.sendPacket(packet);
		}
	}

	public void broadcastPacketInRange(Packet packet, EntityPlayer player) {
		for (EntityPlayer otherPl : player.getServer().getPlayers())
			if (otherPl.getId() != player.getId() && otherPl.isActive() && otherPl.canSee(player))
				otherPl.getSession().sendPacket(packet);
	}

	void add(Session session) {
		synchronized (pending) {
			Validate.isTrue(!pending.contains(session));
			pending.add(session);
		}
	}
}


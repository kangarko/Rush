package net.rush.model.entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.rush.RushServer;
import net.rush.api.ChunkCoords;
import net.rush.api.meta.MetaParam;
import net.rush.model.RushChunk;
import net.rush.model.Session;
import net.rush.protocol.Packet;
import net.rush.protocol.packets.ChatMessage;
import net.rush.protocol.packets.DestroyEntity;
import net.rush.protocol.packets.NamedEntitySpawn;
import net.rush.protocol.packets.PlayerLookAndPosition;
import net.rush.protocol.packets.SpawnPosition;

public final class RushPlayer extends RushTrackeableEntity {

	/**
	 * The normal height of a player's eyes above their feet.
	 */
	public final double NORMAL_EYE_HEIGHT = 1.62D;

	/**
	 * The height of a player's eyes above their feet when they are crouching.
	 */
	public final double CROUCH_EYE_HEIGHT = 1.42D;

	public final String name;
	public final Session session;
	public final RushServer server;
	
	public final HashSet<RushEntity> knownEntities = new HashSet<>();
	public final HashSet<ChunkCoords> knownChunks = new HashSet<>();

	public boolean sprinting = false;
	public boolean crouching = false;
	public boolean onGround = false;

	public RushPlayer(Session session, String name) {
		super(session.server.world);

		this.name = name;
		this.session = session;
		this.server = session.server;
		
		setPosition(world.spawnPosition);

		// stream the initial set of blocks and teleport us
		streamBlocks();

		session.sendPacket(new SpawnPosition(world.spawnPosition)); // compass
		session.sendPacket(new PlayerLookAndPosition(position.x, position.y, position.z, position.y + NORMAL_EYE_HEIGHT, 0F, 0F, true));

		server.getLogger().info(name + " [" + session.removeAddress() + "] logged in with id " + id + " at ([world] x:? y:? z:?)");
		server.broadcastMessage("&3" + name + " &fhas joined the game.");
		
		sendMessage("%Rush Welcome to Rush, " + name);
	}

	public void sendMessage(String message) {
		session.sendPacket(new ChatMessage(message));
	}

	@Override
	public void pulse() {
		super.pulse();
		
		streamBlocks();

		// Spawn new entities.
		for (RushEntity entity : world.entities) {
			if (entity.id == id)
				continue;

			if (!knownEntities.contains(entity) && entity.isActive() && canSee(entity)) {
				knownEntities.add(entity);
				session.sendPacket(entity.createSpawnMessage());
				sendMessage("Recieved metadata of " + entity + " id " + id);
			}
		}
		
		// Update existing entities.
		for (Iterator<RushEntity> it = knownEntities.iterator(); it.hasNext(); ) {
			RushEntity entity = it.next();
			
			if (entity.isActive() && canSee(entity)) {
				Packet updatePacket = entity.createUpdateMessage();

				if (updatePacket != null)
					session.sendPacket(updatePacket);

			} else {
				session.sendPacket(new DestroyEntity(entity.id));
				it.remove();
			}
		}
	}

	/**
	 * Streams chunks to the player's client.
	 */
	public void streamBlocks() {
		Set<ChunkCoords> previousChunks = new HashSet<>(knownChunks);

		int centralX = position.intX() / RushChunk.WIDTH;
		int centralZ = position.intZ() / RushChunk.HEIGHT;

		int viewDistance = server.viewDistance;

		for (int x = (centralX - viewDistance); x <= (centralX + viewDistance); x++)
			for (int z = (centralZ - viewDistance); z <= (centralZ + viewDistance); z++) {
				ChunkCoords key = new ChunkCoords(x, z);

				if (!knownChunks.contains(key)) {
					knownChunks.add(key);
					session.sendPacket(world.getChunkFromChunkCoords(x, z).toPacket());
				}

				previousChunks.remove(key);
			}

		for (ChunkCoords key : previousChunks)
			knownChunks.remove(key);

		previousChunks.clear();
	}


	@Override
	public Packet createSpawnMessage() {		
		return new NamedEntitySpawn(id, name, position, 0, metadata);
	}
	
	public void setCrouching(boolean crouching) {
		this.crouching = crouching;
		setMetadata(new MetaParam<Byte>(0, new Byte((byte) (crouching ? 0x02: 0))));
		// FIXME: other bits in the bitmask would be wiped out
		// TODO: update other clients, needs to be figured out
	}

	public void setSprinting(boolean sprinting) {
		this.sprinting = sprinting;
		setMetadata(new MetaParam<Byte>(0, new Byte((byte) (sprinting ? 0x08: 0))));
		// FIXME: other bits in the bitmask would be wiped out
	}

	public double getEyeHeight() {
		return crouching ? CROUCH_EYE_HEIGHT : NORMAL_EYE_HEIGHT;
	}
}


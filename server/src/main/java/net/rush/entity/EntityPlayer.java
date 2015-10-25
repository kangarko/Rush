package net.rush.entity;

import java.util.Iterator;

import lombok.Getter;
import lombok.Setter;
import net.rush.api.ChatColor;
import net.rush.api.GameMode;
import net.rush.api.meta.MetaParam;
import net.rush.api.safety.SafeUnorderedZoznam;
import net.rush.model.CommandSender;
import net.rush.model.Session;
import net.rush.protocol.Packet;
import net.rush.protocol.packets.ChangeGameState;
import net.rush.protocol.packets.ChangeGameState.GameStateReason;
import net.rush.protocol.packets.ChatMessage;
import net.rush.protocol.packets.DestroyEntity;
import net.rush.protocol.packets.PlayerListItem;
import net.rush.protocol.packets.PlayerLookAndPosition;
import net.rush.protocol.packets.SpawnPlayer;
import net.rush.protocol.packets.SpawnPosition;
import net.rush.protocol.packets.TimeUpdate;
import net.rush.utils.JsonUtils;
import net.rush.world.chunk.Chunk;
import net.rush.world.chunk.ChunkCoords;

public class EntityPlayer extends EntityTrackeable implements CommandSender {

	/**
	 * The normal height of a player's eyes above their feet.
	 */
	public final static double NORMAL_EYE_HEIGHT = 1.62D;

	/**
	 * The height of a player's eyes above their feet when crouching.
	 */
	public final static double CROUCH_EYE_HEIGHT = 1.42D;
	
	private final SafeUnorderedZoznam<Entity> knownEntities = new SafeUnorderedZoznam<>();
	private final SafeUnorderedZoznam<ChunkCoords> knownChunks = new SafeUnorderedZoznam<>();
	@Getter
	private final Session session;
	@Getter
	private GameMode gamemode = GameMode.SURVIVAL;
	
	@Getter
	private final String name;
	@Getter
	private boolean sprinting = false;
	@Getter
	private boolean crouching = false;
	@Getter
	@Setter
	private boolean onGround = false;
	
	public EntityPlayer(Session session, String name) {
		super(session.getServer().getWorld());
		
		this.name = name;
		this.session = session;
		
		setMetadata(new MetaParam<Float>(6, 20F)); // Health. TODO move to LivingEntity
		setPosition(world.getSpawnPosition());

		streamBlocks();
		updateTabList();

		session.sendPacket(new SpawnPosition(world.getSpawnPosition())); // compass
		session.sendPacket(new PlayerLookAndPosition(position.x, position.y, position.z, 0F, 0F, true));
		session.sendPacket(new TimeUpdate(world.getTime(), world.getTime()));
		
		server.getLogger().info(name + " [" + session.getIp() + "] logged in with id " + id + " at ([world] " + position + ")");
		server.broadcastMessage("&3" + name + " &fhas joined the game.");

		sendMessage("%Rush Welcome to Rush, " + name);
	}

	public void sendMessage(String message) {
		session.sendPacket(new ChatMessage(JsonUtils.jsonizePlainText(message)));
	}

	@Override
	public void onPulse() {
		streamBlocks();

		// Spawn new entities.
		for (Entity entity : world.getEntities()) {
			if (entity.id == id)
				continue;

			if (!knownEntities.contains(entity) && entity.isActive() && canSee(entity)) {
				knownEntities.add(entity);
				session.sendPacket(entity.createSpawnMessage());
				sendMessage(ChatColor.GRAY + "You now see the " + ChatColor.GREEN + entity);
				System.out.println(this + " has received spawn packet of " + entity);
			}
		}
		
		// Update existing entities.
		for (Iterator<Entity> it = knownEntities.iterator(); it.hasNext(); ) {
			Entity entity = it.next();

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
	private void streamBlocks() {
		SafeUnorderedZoznam<ChunkCoords> previousChunks = new SafeUnorderedZoznam<>(knownChunks);

		int centralX = position.intX() / Chunk.WIDTH;
		int centralZ = position.intZ() / Chunk.HEIGHT;

		int viewDistance = server.getViewDistance();

		for (int x = (centralX - viewDistance); x <= (centralX + viewDistance); x++)
			for (int z = (centralZ - viewDistance); z <= (centralZ + viewDistance); z++) {
				ChunkCoords key = new ChunkCoords(x, z);

				if (!knownChunks.contains(key)) {					
					Chunk chunk = world.getChunkFromChunkCoords(x, z);					
					knownChunks.add(key);
					
					session.sendPacket(chunk.toPacket());
				
					world.forcePopulate(chunk);
				}

				previousChunks.remove(key);
			}

		for (ChunkCoords key : previousChunks)
			knownChunks.remove(key);

		previousChunks.clear();
	}

	private void updateTabList() {
		PlayerListItem thisPlayer = new PlayerListItem(name, true, 0);

		for(EntityPlayer otherPlayer : server.getPlayers()) {
			otherPlayer.session.sendPacket(thisPlayer);
			session.sendPacket(new PlayerListItem(otherPlayer.name, true, 0));
		}
	}

	private void removeFromTabList() {
		PlayerListItem playerToRemove = new PlayerListItem(name, false, 0);

		for(EntityPlayer otherPlayer : server.getPlayers())
			if (otherPlayer.id == id)
				continue;
			else
				otherPlayer.session.sendPacket(playerToRemove);		
	}

	@Override
	public Packet createSpawnMessage() {		
		return new SpawnPlayer(id, name, position, 0, metadata);
	}
	
	public void onDisconnect() {
		server.broadcastMessage("&3" + name + " &fleft the game.");
		removeFromTabList();
	}
	
	public void kickPlayer(String reason) {
		session.disconnect(reason);
	}
	
	public boolean isOnline() {
		return session.hasPlayer();
	}

	public void setCrouching(boolean crouching) {
		this.crouching = crouching;
		setMetadata(new MetaParam<Byte>(0, (byte) (crouching ? 0x02: 0)));
		// FIXME: other bits in the bitmask would be wiped out
		// TODO: update other clients, needs to be figured out
	}

	public void setSprinting(boolean sprinting) {
		this.sprinting = sprinting;
		setMetadata(new MetaParam<Byte>(0, (byte) (sprinting ? 0x08: 0)));
		// FIXME: other bits in the bitmask would be wiped out
	}
	
	public void setGamemode(GameMode newgamemode) {
		if (gamemode == newgamemode)
			return;
		
		gamemode = newgamemode;
		
		session.sendPacket(new ChangeGameState(GameStateReason.CHANGE_GAMEMODE, gamemode.getValue()));
	}
	
	public double getEyeHeight() {
		return crouching ? CROUCH_EYE_HEIGHT : NORMAL_EYE_HEIGHT;
	}

	@Override
	public String toString() {
		return name == null ? super.toString() : name + " id " + id;
	}

	@Override
	public boolean isPlayer() {
		return true;
	}
}


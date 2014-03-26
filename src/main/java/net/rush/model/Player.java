package net.rush.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.rush.Server;
import net.rush.chunk.Chunk;
import net.rush.chunk.ChunkCoords;
import net.rush.inventory.Inventory;
import net.rush.inventory.PlayerInventory;
import net.rush.net.Session;
import net.rush.packets.Packet;
import net.rush.packets.packet.impl.ChangeGameStatePacketImpl;
import net.rush.packets.packet.impl.ChatPacketImpl;
import net.rush.packets.packet.impl.DestroyEntityPacketImpl;
import net.rush.packets.packet.impl.NamedEntitySpawnPacketImpl;
import net.rush.packets.packet.impl.PlayerListItemPacketImpl;
import net.rush.packets.packet.impl.PlayerPositionAndLookPacketImpl;
import net.rush.packets.packet.impl.SetWindowItemsPacketImpl;
import net.rush.packets.packet.impl.SpawnPositionPacketImpl;
import net.rush.util.Parameter;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

/**
 * Represents an in-game player.
 */
public final class Player extends LivingEntity implements CommandSender {

	/**
	 * The normal height of a player's eyes above their feet.
	 */
	public static final double NORMAL_EYE_HEIGHT = 1.62D;

	/**
	 * The height of a player's eyes above their feet when they are crouching.
	 */
	public static final double CROUCH_EYE_HEIGHT = 1.42D;

	private final String name;
	private GameMode gamemode;
	private boolean sprinting = false;
	
	private PlayerInventory inventory = new PlayerInventory();
	
	/**
	 * This player's session.
	 */
	private final Session session;

	/**
	 * The entities that the client knows about.
	 */
	private Set<Entity> knownEntities = new HashSet<Entity>();

	/**
	 * The chunks that the client knows about.
	 */
	private Set<ChunkCoords> knownChunks = new HashSet<ChunkCoords>();

	/**
	 * A flag that indicates if this player is crouching.
	 */
	private boolean crouching = false;

	/**
	 * Creates a new player and adds it to the world.
	 * @param session The player's session.
	 * @param name The player's name.
	 */
	public Player(Session session, String name, GameMode gamemode) {
		super(session.getServer().getWorld(), EntityType.PLAYER);
		this.name = name;
		this.session = session;
		this.gamemode = gamemode;

		// stream the initial set of blocks and teleport us
		this.streamBlocks();
		this.position = world.getSpawnPosition();

		this.session.send(new SpawnPositionPacketImpl(position));
		this.session.send(new PlayerPositionAndLookPacketImpl(position.getX(), position.getY(), position.getZ(), position.getY() + NORMAL_EYE_HEIGHT, (float) rotation.getYaw(), (float) rotation.getPitch(), true));

		this.sendMessage("&3Rush // &fWelcome to Rush, " + name);
		
		Server.getLogger().info(getName() + "[" + getSession().getRemoveAddress() + "] logged in with entity id " + getId());
		for(Player pl : session.getServer().getWorld().getRushPlayers()) {
			pl.getSession().send(new PlayerListItemPacketImpl(name, true, (short)100));
			session.send(new PlayerListItemPacketImpl(pl.getName(), true, (short)100));
		}
	}

	/**
	 * Gets the name of this player.
	 * @return The name of this player.
	 */
	public String getName() {
		return name;
	}

	/**
	 * A convenience method for sending a message to this player.
	 * @param message The message.
	 */
	public void sendMessage(String message) {
		session.send(new ChatPacketImpl(ChatColor.translateAlternateColorCodes("&".charAt(0), message)));
	}

	@Override
	public void pulse() {
		super.pulse();

		streamBlocks();

		for (Iterator<Entity> it = knownEntities.iterator(); it.hasNext(); ) {
			Entity entity = it.next();
			boolean withinDistance = entity.isActive() && isWithinDistance(entity);

			if (withinDistance) {
				Packet msg = entity.createUpdateMessage();
				if (msg != null)
					session.send(msg);
			} else {
				session.send(new DestroyEntityPacketImpl(new int[]{entity.getId()} ));
				it.remove();
			}
		}

		for (Entity entity : world.getRushEntities()) {
			if (entity == this)
				continue;
			boolean withinDistance = entity.isActive() && isWithinDistance(entity);

			if (withinDistance && !knownEntities.contains(entity)) {
				knownEntities.add(entity);
				session.send(entity.createSpawnMessage());
			}
		}
	}
 
	/**
	 * Streams chunks to the player's client.
	 */
	private void streamBlocks() {
		Set<ChunkCoords> previousChunks = new HashSet<ChunkCoords>(knownChunks);

		int centralX = ((int) position.getX()) / Chunk.WIDTH;
		int centralZ = ((int) position.getZ()) / Chunk.HEIGHT;

		for (int x = (centralX - Chunk.VISIBLE_RADIUS); x <= (centralX + Chunk.VISIBLE_RADIUS); x++) {
			for (int z = (centralZ - Chunk.VISIBLE_RADIUS); z <= (centralZ + Chunk.VISIBLE_RADIUS); z++) {
				ChunkCoords key = new ChunkCoords(x, z);
				if (!knownChunks.contains(key)) {
					knownChunks.add(key);
					//session.send(new PreChunkPacketImpl(x, z, true));
					session.send(world.getChunks().getChunk(x, z).toMessage());
				}
				previousChunks.remove(key);
			}
		}

		for (ChunkCoords key : previousChunks) {
			//session.send(new PreChunkPacketImpl(key.x, key.z, false));
			knownChunks.remove(key);
		}

		previousChunks.clear();
	}

	/**
	 * Gets the session.
	 * @return The session.
	 */
	public Session getSession() {
		return session;
	}

	@Override
	public Packet createSpawnMessage() {
		int x = position.getPixelX();
		int y = position.getPixelY();
		int z = position.getPixelZ();
		int yaw = rotation.getIntYaw();
		int pitch = rotation.getIntPitch();
		return new NamedEntitySpawnPacketImpl(id, name, new Position(x, y, z), (byte)yaw, (byte)pitch, (byte)0, metadata.clone());
	}

	/**
	 * Sets the crouching flag.
	 * @param crouching The crouching flag.
	 */
	public void setCrouching(boolean crouching) {
		// TODO: update other clients, needs to be figured out
		this.crouching = crouching;
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 0, new Byte((byte) (crouching ? 0x02: 0))));
		// FIXME: other bits in the bitmask would be wiped out
	}

	/**
	 * Gets the crouching flag.
	 * @return The crouching flag.
	 */
	public boolean isCrouching() {
		return crouching;
	}
	
	public boolean isSprinting() {
		return sprinting;
	}
	
	public void setSprinting(boolean sprinting) {
		this.sprinting = sprinting;
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 0, new Byte((byte) (sprinting ? 0x08: 0))));
		// FIXME: other bits in the bitmask would be wiped out
	}
	
	public GameMode getGamemode() {
		return gamemode;
	}
	
	@SuppressWarnings("deprecation")
	public void setGamemode(GameMode gamemode) {
		this.gamemode = gamemode;
		this.getSession().send(new ChangeGameStatePacketImpl((byte)3, (byte)gamemode.getValue()));
	}
	
	// Inventory
	
    public PlayerInventory getInventory() {
        return inventory;
    }

    public ItemStack getItemInHand() {
        return inventory.getItemInHand();
    }

    public void setItemInHand(ItemStack paramItemStack) {
        inventory.setItemInHand(paramItemStack);
    }

    // FIXME don´t work, yet
	public void onSlotSet(Inventory inv, int index, ItemStack itemStack) {
		getSession().send(new SetWindowItemsPacketImpl(inv.getId(), index, new Item[] {new Item(itemStack.getTypeId(), itemStack.getAmount(), itemStack.getData().getData())}));
		System.out.println("sending inv packet");
	}

	public Server getServer() {
		return session.getServer();
	}
}


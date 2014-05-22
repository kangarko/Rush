package net.rush.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.rush.Server;
import net.rush.chunk.Chunk;
import net.rush.chunk.ChunkCoords;
import net.rush.inventory.Inventory;
import net.rush.inventory.PlayerInventory;
import net.rush.model.entity.ItemEntity;
import net.rush.net.Session;
import net.rush.packets.Packet;
import net.rush.packets.packet.ChangeGameStatePacket;
import net.rush.packets.packet.ChatPacket;
import net.rush.packets.packet.DestroyEntityPacket;
import net.rush.packets.packet.EntityEquipmentPacket;
import net.rush.packets.packet.NamedEntitySpawnPacket;
import net.rush.packets.packet.NamedSoundEffectPacket;
import net.rush.packets.packet.OpenWindowPacket;
import net.rush.packets.packet.PlayerListItemPacket;
import net.rush.packets.packet.PlayerPositionAndLookPacket;
import net.rush.packets.packet.SetSlotPacket;
import net.rush.packets.packet.SoundOrParticleEffectPacket;
import net.rush.packets.packet.SpawnPositionPacket;
import net.rush.packets.packet.UpdateHealthPacket;
import net.rush.util.MathHelper;
import net.rush.util.Parameter;
import net.rush.util.enums.GameStateReason;
import net.rush.util.enums.InventoryEnum;

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;

/**
 * Represents an in-game player.
 */
public final class Player extends LivingEntity implements CommandSender {

	/**
	 * The normal height of a player's eyes above their feet.
	 */
	private final double NORMAL_EYE_HEIGHT = 1.62D;

	/**
	 * The height of a player's eyes above their feet when they are crouching.
	 */
	private final double CROUCH_EYE_HEIGHT = 1.42D;

	private final String name;
	private GameMode gamemode;
	private boolean sprinting = false;
	private boolean riding = false;
	private boolean onGround = true;

	private float exhaustion = 0F;
	private int food = 20;
	private float saturation = 0;

	private final PlayerInventory inventory = new PlayerInventory();

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

	private ItemStack itemOnCursor = ItemStack.NULL_ITEMSTACK;
	
	private int windowId = 0;

	/**
	 * Creates a new player and adds it to the world.
	 * @param session The player's session.
	 * @param name The player's name.
	 */
	@SuppressWarnings("deprecation")
	public Player(Session session, String name) {
		super(session.getServer().getWorld(), EntityType.PLAYER);

		this.maxHealth = 20;
		this.name = name;
		this.session = session;
		this.gamemode = GameMode.getByValue(session.getServer().getProperties().gamemode);
		this.position = world.getSpawnPosition();

		this.inventory.addViewer(this);

		// stream the initial set of blocks and teleport us
		this.streamBlocks();

		// display player in the TAB list
		this.updateTabList();

		this.session.send(new SpawnPositionPacket(position));
		this.session.send(new PlayerPositionAndLookPacket(position.getX(), position.getY(), position.getZ(), position.getY() + NORMAL_EYE_HEIGHT, (float) rotation.getYaw(), (float) rotation.getPitch(), true));

		getServer().getLogger().info(name + " [" + session.getIp() + "] logged in with entity id " + id + " at ([" + world.getName() + "] " + (int)position.getX() + ", " + (int)position.getY() + ", " + (int)position.getZ() + ")");
		getServer().broadcastMessage("&e" + name + " has joined the game.");
		this.sendMessage("&3Rush // &fWelcome to Rush, " + name);
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
		session.send(new ChatPacket(message));
	}

	public void playSound(String soundName, double x, double y, double z, float volume, float pitch) {
		session.send(new NamedSoundEffectPacket(soundName, x, y, z, volume, pitch));
	}

	public void playSound(String soundName, Position pos, float volume, float pitch) {
		session.send(new NamedSoundEffectPacket(soundName, pos.getX(), pos.getY(), pos.getZ(), volume, pitch));
	}

	public void playEffect(int effectId, int x, int y, int z, int data) {
		session.send(new SoundOrParticleEffectPacket(effectId, x, y, z, data, false));
	}

	public void updateTabList() {
		Packet newPlayer = new PlayerListItemPacket(name, true, (short)100);

		for(Player pl : getWorld().getPlayers()) {
			pl.getSession().send(newPlayer);
			session.send(new PlayerListItemPacket(pl.getName(), true, (short)100));
		}
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
				
				if(!announced) {
					System.out.println("Created Update Message Packet - closing debug");
					announced = true;
				}
					
			} else {
				session.send(new DestroyEntityPacket(entity.getId()));
				it.remove();
			}
		}

		for (Entity entity : world.getEntities()) {
			if (entity == this)
				continue;
			boolean withinDistance = entity.isActive() && isWithinDistance(entity);

			if (withinDistance && !knownEntities.contains(entity)) {
				knownEntities.add(entity);
				session.send(entity.createSpawnMessage());
				
				if(!announced)
					System.out.println("Created Spawn Packet");
			}
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (ticksLived % 20 * 12 == 0)
			heal();

		/*if (getHealth() > 0) {
			List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1.0D, 0.0D, 1.0D));
			if (list != null)
				for (int index = 0; index < list.size(); index++) {
					Entity entity = list.get(index);
					if (entity.isActive() && entity instanceof Player)
						onCollideWithPlayer((Player) entity);
				}
		}*/
	}

	/**
	 * Streams chunks to the player's client.
	 */
	private void streamBlocks() {
		Set<ChunkCoords> previousChunks = new HashSet<ChunkCoords>(knownChunks);

		int centralX = ((int) position.getX()) / Chunk.WIDTH;
		int centralZ = ((int) position.getZ()) / Chunk.HEIGHT;

		int viewDistance = Server.getServer().getProperties().viewDistance;

		for (int x = (centralX - viewDistance); x <= (centralX + viewDistance); x++) {
			for (int z = (centralZ - viewDistance); z <= (centralZ + viewDistance); z++) {
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
		return new NamedEntitySpawnPacket(id, name, new Position(x, y, z), (byte)yaw, (byte)pitch, (byte)0, metadata.clone());
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

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	@SuppressWarnings("deprecation")
	public void setGamemode(GameMode gamemode) {
		this.gamemode = gamemode;
		this.getSession().send(new ChangeGameStatePacket(GameStateReason.CHANGE_GAMEMODE, gamemode.getValue()));
	}

	public boolean isRiding() {
		return riding;
	}

	public void setRiding(boolean riding) {
		this.riding = riding;
	}

	public void addExhaustion(float exhaustion) {
		this.exhaustion+= exhaustion;
	}

	public float getExhaustion() {
		return exhaustion;
	}

	public double getEyeHeight() {
		return isCrouching() ? CROUCH_EYE_HEIGHT : NORMAL_EYE_HEIGHT;
	}

	public void throwItemFromPlayer(ItemStack itemstack) {
		if (itemstack == ItemStack.NULL_ITEMSTACK)
			return;

		ItemEntity item = new ItemEntity(world, getPosition().getX(), getPosition().getY() - 0.30000001192092896D + 0.12F, getPosition().getZ(), itemstack);
		item.pickupDelay = 40;

		float offsetX = 0.1F;
		float offsetZ;

		offsetX = 0.3F;
		item.motionX = -MathHelper.sin((float)getRotation().getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos((float)getRotation().getPitch() / 180.0F * (float) Math.PI) * offsetX;
		item.motionZ = MathHelper.cos((float)getRotation().getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos((float)getRotation().getPitch() / 180.0F * (float) Math.PI) * offsetX;
		item.motionY = -MathHelper.sin((float)getRotation().getPitch() / 180.0F * (float) Math.PI) * offsetX + 0.1F;

		offsetX = 0.02F;
		offsetZ = rand.nextFloat() * (float) Math.PI * 2.0F;
		offsetX *= rand.nextFloat();

		item.motionX += Math.cos(offsetZ) * offsetX;
		item.motionY += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
		item.motionZ += Math.sin(offsetZ) * offsetX;

		item.throwerId = this.id;
	}
	
	public void openInventory(InventoryEnum type) {
		openInventory(type, 9, "", -1);
	}
	
	public void openInventory(InventoryEnum type, int slots, String name) {
		openInventory(type, slots, name, -1);
	}
	
	public void openInventory(InventoryEnum type, int slots, String name, int horseId) {
		windowId++;		
		session.send(new OpenWindowPacket(windowId, type.id, name, slots, name != "", horseId));
	}

	// Inventory

	public PlayerInventory getInventory() {
		return inventory;
	}

	public ItemStack getItemInHand() {
		return inventory.getItemInHand();
	}

	public void setItemInHand(ItemStack item) {
		inventory.setItemInHand(item);
	}

	public ItemStack getItemOnCursor() {
		return itemOnCursor;
	}

	public Set<Entity> getKnownEntities() {
		return knownEntities;
	}

	public void setItemOnCursor(ItemStack item) {
		itemOnCursor = item;
		if (item == null) {
			session.send(new SetSlotPacket(1, inventory.getHeldItemSlot(), ItemStack.NULL_ITEMSTACK));
		} else {
			session.send(new SetSlotPacket(1, inventory.getHeldItemSlot(), item));
		}
	}

	public void onSlotSet(Inventory inv, int index, ItemStack item) {
		//getSession().send(new SetSlotPacket(0, index, item));

		int type = item == null ? -1 : item.getId();
		int data = item == null ? 0 : item.getDamage();

		int equipSlot = -1;

		if (index == getInventory().getHeldItemSlot()) {
			equipSlot = EntityEquipmentPacket.HELD_ITEM;

		} else if (index == PlayerInventory.HELMET_SLOT) {
			equipSlot = EntityEquipmentPacket.HELMET_SLOT;

		} else if (index == PlayerInventory.CHESTPLATE_SLOT) {
			equipSlot = EntityEquipmentPacket.CHESTPLATE_SLOT;

		} else if (index == PlayerInventory.LEGGINGS_SLOT) {
			equipSlot = EntityEquipmentPacket.LEGGINGS_SLOT;

		} else if (index == PlayerInventory.BOOTS_SLOT) {
			equipSlot = EntityEquipmentPacket.BOOTS_SLOT;
		}

		if (equipSlot >= 0) {
			EntityEquipmentPacket message = new EntityEquipmentPacket(getId(), (short)equipSlot, (short)type, (short)data);
			for (Player player : getWorld().getPlayers())
				if (player != this && player.isWithinDistance(this))
					player.getSession().send(message);
		}


		if (item == null) {
			session.send(new SetSlotPacket(inventory.getId(), index, ItemStack.NULL_ITEMSTACK));
		} else {
			session.send(new SetSlotPacket(inventory.getId(), index, item));
		}
	}

	public Server getServer() {
		return session.getServer();
	}

	public void heal() {
		if(health < maxHealth) {
			setHealth(health + 1);
			getSession().send(new UpdateHealthPacket(health, (short)food, saturation));
		}
	}
}


package net.rush.protocol;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import net.rush.api.ChatColor;
import net.rush.api.Difficulty;
import net.rush.api.Environment;
import net.rush.api.GameMode;
import net.rush.block.Block;
import net.rush.entity.EntityPlayer;
import net.rush.model.Session;
import net.rush.protocol.packets.Animation;
import net.rush.protocol.packets.ChatMessage;
import net.rush.protocol.packets.ClientSettings;
import net.rush.protocol.packets.Digging;
import net.rush.protocol.packets.Digging.DiggingStatus;
import net.rush.protocol.packets.EntityAction;
import net.rush.protocol.packets.EntityHeadLook;
import net.rush.protocol.packets.Handshake;
import net.rush.protocol.packets.JoinGame;
import net.rush.protocol.packets.KeepAlive;
import net.rush.protocol.packets.LoginStart;
import net.rush.protocol.packets.LoginSuccess;
import net.rush.protocol.packets.PlayerLook;
import net.rush.protocol.packets.PlayerLookAndPosition;
import net.rush.protocol.packets.PlayerOnGround;
import net.rush.protocol.packets.PlayerPosition;
import net.rush.protocol.packets.PluginMessage;
import net.rush.protocol.packets.StatusPing;
import net.rush.protocol.packets.StatusRequest;
import net.rush.protocol.packets.StatusResponse;
import net.rush.world.World;

public class PacketHandler {

	private Logger logger = Logger.getLogger("Minecraft");

	public <T extends Packet> void handle(Session session, T packet) {
		try {			
			getClass().getMethod("handle", Session.class, packet.getClass()).invoke(this, session, packet);
		
		} catch (NoSuchMethodException ex) {
			logger.info("Missing handler for packet: " + packet.getClass().getSimpleName());

		} catch (ReflectiveOperationException ex) {
			ex.printStackTrace();
		}
	}

	public void handle(Session session, KeepAlive packet) {
		if (session.getPingToken() == packet.getToken())
			session.pong();
	}

	public void handle(Session session, ChatMessage packet) {
		String text = packet.getMessage();

		if (text == null || "".equals(text))
			session.disconnect("Cannot send an empty message");
		
		else if (text.length() > 110)
			session.disconnect("Chat message too long");
		
		else {			
			if (text.matches("(&([a-f0-9k-or]))"))
				return;

			if (text.startsWith("/")) {
				logger.info(session.getPlayer().getName() + " issued server command: " + text);
				session.getServer().getCommandManager().dispatchCommand(session.getPlayer(), text.substring(1));

			} else {
				text = session.getPlayer().getName() + ": " + text.replaceAll("\\s+", " ").trim();

				logger.info(text.replaceAll("(&([a-f0-9k-or]))", ""));
				session.getServer().broadcastMessage(text);
			}
		}
	}

	public void handle(Session session, Handshake packet) {
		session.protocol = packet.getProtocolVer();
	}

	public void handle(Session session, Animation packet) {
		packet = new Animation(session.getPlayer().getId(), packet.getAnimation());		
		session.getServer().getSessionRegistry().broadcastPacketInRange(packet, session.getPlayer());
	}

	public void handle(Session session, EntityAction packet) {
		EntityPlayer player = session.getPlayer();

		switch (packet.getAction()) {
			case ACTION_CROUCH:
				player.setCrouching(true);
				break;
			case UNCROUCH:
				player.setCrouching(false);
				break;
			case START_SPRINTING:
				player.setSprinting(true);
				break;
			case STOP_SPRINTING:
				player.setSprinting(false);
				break;
			case LEAVE_BED:
				break;
			default:
				throw new RuntimeException("Unhandled action: " + packet.getAction());
		}
	}

	public void handle(Session session, Digging packet) {		
		EntityPlayer player = session.getPlayer();
		World world = player.getWorld();

		int x = packet.getX();
		int z = packet.getZ();
		int y = packet.getY();
		
		int id = world.getType(x, y, z);
		
		if (!Block.exists(id)) {
			player.sendMessage("&cUnknown broken block ID " + world.getType(x, y, z));
			return;
		}
		
		Block block = Block.byId(id);
		int metadata = world.getMetadata(x, y, z);
		
		if ((player.getGamemode() == GameMode.CREATIVE && packet.getStatus() == DiggingStatus.START) 
				|| (player.getGamemode() == GameMode.SURVIVAL && packet.getStatus() == DiggingStatus.DONE)
				/*|| (packet.getStatus() == DiggingStatus.START && block.getHardness() == 0)*/) {

			block.onBlockDestroy(world, player, x, y, z, metadata);

			/*if (player.getGamemode() != GameMode.CREATIVE) {
				block.dropBlock(world, x, y, z, metadata, 0);

				if(player.getItemInHand() != null && player.getItemInHand() != null && player.getItemInHand().getId() != 0 )
					player.getInventory().takeOrDamageItemInHand(player, true);
			}*/

			world.setAir(x, y, z);
			//world.playEffectExceptTo(Effect.STEP_SOUND, x, y, z, block.id, player);
			player.sendMessage("&7Registered block break of &3" + block.getClass().getSimpleName().replace("Block", "") + " &7id &3" + block.id);
		}		
	}
	
	public void handle(Session session, ClientSettings packet) {
	}

	public void handle(Session session, PluginMessage packet) {
	}

	public void handle(Session session, PlayerPosition packet) {
		handlePosition(session, true, false, packet);
	}

	public void handle(Session session, PlayerLook packet) {
		handlePosition(session, false, true, packet);
	}

	public void handle(Session session, PlayerLookAndPosition packet) {
		handlePosition(session, true, true, packet);
	}

	public void handle(Session session, PlayerOnGround packet) {
		handlePosition(session, false, false, packet);
	}

	public void handlePosition(Session session, boolean hasPos, boolean hasLook, PlayerOnGround packet) {		
		EntityPlayer player = session.getPlayer();

		if (hasPos)
			player.setPosition(packet.getX(), packet.getFeetY(), packet.getZ());

		if (hasLook) {
			player.getPosition().setRotation(packet.getYaw(), packet.getPitch());
			session.getServer().getSessionRegistry().broadcastPacketExcept(new EntityHeadLook(player.getId(), player.getPosition().getIntYaw()), player.getId()); // TODO Fix incorrect yaw.
		}

		player.setOnGround(packet.isOnGround());
	}

	public void handle(Session session, LoginStart packet) {
		if (session.protocol != 4 && session.protocol != 5)			
			session.disconnect("Outdated client! Connect with 1.7.x");			
		else {
			EntityPlayer existing = session.getServer().getPlayer(packet.getName());

			if (existing != null)
				existing.kickPlayer(ChatColor.RED + "You are logged from another location");
			
			session.sendPacket(new LoginSuccess("0-0-0-0-0", packet.getName()));
			session.sendPacket(new JoinGame(0, "DEFAULT", GameMode.SURVIVAL, Environment.NORMAL, Difficulty.NORMAL, 256, 20, false));
			session.startJoining(packet.getName());
		}
	}

	// ------------------ Animated Motd ------------------ //
	private int counter;
	private long time = 0;

	private final Timer motdTimer = new Timer("Anim Motd");
	private final String motdColorDef = "&f&l";
	private final String motdC1 = "&e&l";
	private final String motdC2 = "&6&l";
	private final String[] motdParts = {
			motdColorDef + "Checking spawn area...",
			motdColorDef + motdC1 + "C" + motdC2 + "h" + motdColorDef + "ecking spawn area...",
			motdColorDef + "C" + motdC1 + "h" + motdC2 + "e" + motdColorDef + "cking spawn area...",
			motdColorDef + "Ch" + motdC1 + "e" + motdC2 + "c" + motdColorDef + "king spawn area...",
			motdColorDef + "Che" + motdC1 + "c" + motdC2 + "k" + motdColorDef + "ing spawn area...",
			motdColorDef + "Chec" + motdC1 + "k" + motdC2 + "i" + motdColorDef + "ng spawn area...",
			motdColorDef + "Check" + motdC1 + "i" + motdC2 + "n" + motdColorDef + "g spawn area...",
			motdColorDef + "Checki" + motdC1 + "n" + motdC2 + "g" + motdColorDef + " spawn area...",
			motdColorDef + "Checkin" + motdC1 + "g" + motdC2 + " " + motdColorDef + "spawn area...",
			motdColorDef + "Checking" + motdC1 + " " + motdC2 + "s" + motdColorDef + "pawn area...",
			motdColorDef + "Checking " + motdC1 + "s" + motdC2 + "p" + motdColorDef + "awn area...",
			motdColorDef + "Checking s" + motdC1 + "p" + motdC2 + "a" + motdColorDef + "wn area...",
			motdColorDef + "Checking sp" + motdC1 + "a" + motdC2 + "w" + motdColorDef + "n area...",
			motdColorDef + "Checking spa" + motdC1 + "w" + motdC2 + "n" + motdColorDef + " area...",
			motdColorDef + "Checking spaw" + motdC1 + "n" + motdC2 + " " + motdColorDef + "area...",
			motdColorDef + "Checking spawn" + motdC1 + " " + motdC2 + "a" + motdColorDef + "rea...",
			motdColorDef + "Checking spawn " + motdC1 + "a" + motdC2 + "r" + motdColorDef + "ea...",
			motdColorDef + "Checking spawn a" + motdC1 + "r" + motdC2 + "e" + motdColorDef + "a...",
			motdColorDef + "Checking spawn ar" + motdC1 + "e" + motdC2 + "a" + motdColorDef + "...",
			motdColorDef + "Checking spawn are" + motdC1 + "a" + motdC2 + "." + motdColorDef + "..",
			motdColorDef + "Checking spawn area" + motdC1 + "." + motdC2 + "." + motdColorDef + ".",
			motdColorDef + "Checking spawn area." + motdC1 + "." + motdC2 + "." + motdColorDef,
			motdColorDef + "Checking spawn area.." + motdC1 + "." + motdC2,
			motdColorDef + "Checking spawn area...",
			"                      &b&lRush Server\\n                 &9&l>>  &6Join &enow  &9&l<<",
	};

	public void handle(Session session, StatusPing packet) {
		time = packet.getTime();
	}

	public void handle(Session session, StatusRequest packet) {
		ServerPing response = new ServerPing(new ServerPing.Protocol("1.7.10", session.protocol), motdParts[0], "", new ServerPing.Players(20, 0));
		session.sendPacket(new StatusResponse(response));

		counter = 1;

		motdTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if (counter < motdParts.length) {
					response.setDescription(motdParts[counter++]);
					session.sendPacket(new StatusResponse(response));
				} else {
					session.sendPacket(new StatusPing(time));
					this.cancel();
				}
			}
		}, 35, 35);
	}
}

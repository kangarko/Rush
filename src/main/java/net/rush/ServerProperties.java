package net.rush;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;

public class ServerProperties {
	
	public String genSettings;
	public int opPermLevel;
	public boolean allowNether;
	public String levelName;
	public boolean enableQuery;
	public boolean allowFlight;
	public boolean announceAchievements;
	public int port;
	public String levelType;
	public boolean enableRcon;
	public String seed;;
	public boolean forceGamemode;
	public String serverIp;
	public int maxBuildHeight;
	public boolean spawnNpcs;
	public boolean whiteList;
	public boolean spawnAnimals;
	public boolean hardcore;
	public boolean snooper;
	public boolean onlineMode;
	public String resourcePack;
	public boolean pvp;
	public int difficulty;
	public boolean enableCmdBlock;
	public int gamemode;
	public int idleTimeout;
	public int maxPlayers;
	public boolean spawnMonsters;
	public boolean generateStructures;
	public int viewDistance;
	public String motd;
	
	private final Properties prop = new Properties();
	private final Logger logger = Logger.getLogger("Minecraft");
	private final File file;
	
	protected ServerProperties(String fileName) {
		this.file = new File(fileName);

		if (file.exists()) {
			Reader reader = null;

			try {
				reader = new FileReader(file);
				prop.load(reader);

			} catch (Exception ex) {
				logger.log(Level.WARNING, "Failed to load " + file, ex);
				generateNew();

			}
		} else
			generateNew();
	}	
	
	public void load() {
		genSettings = getString("generator-settings", "");
		opPermLevel = getInt("op-permission-level", 4);
		allowNether = getBoolean("allow-nether", true);
		levelName = getString("level-name", "world");
		enableQuery = getBoolean("enable-query", false);
		allowFlight = getBoolean("allow-flight", false);
		announceAchievements = getBoolean("announce-player-achievements", true);
		port = getInt("server-port", 25565);
		levelType = getString("level-type", "DEFAULT");
		enableRcon = getBoolean("enable-rcon", false);
		seed = getString("level-seed", "");;
		forceGamemode = getBoolean("force-gamemode", false);
		serverIp = getString("server-ip", "");
		maxBuildHeight = getInt("max-build-height", 256);
		spawnNpcs = getBoolean("spawn-npcs", true);
		whiteList = getBoolean("white-list", false);
		spawnAnimals = getBoolean("spawn-animals", true);
		hardcore = getBoolean("hardcore", false);
		snooper = getBoolean("snooper-enabled", false);
		onlineMode = getOnlineMode();
		resourcePack = getString("resource-pack", "");
		pvp = getBoolean("pvp", true);
		difficulty = getDifficulty();
		enableCmdBlock = getBoolean("enable-command-block", false);
		gamemode = getGamemode(); // TODO
		idleTimeout = getInt("player-idle-timeout", 0);
		maxPlayers = getInt("max-players", 20);
		spawnMonsters = getBoolean("spawn-monsters", true);
		generateStructures = getBoolean("generate-structures", true);
		viewDistance = getInt("view-distance", 10);
		motd = ChatColor.translateAlternateColorCodes('&', getString("motd", "A Rush server"));
	}
	
	boolean getOnlineMode() {
		boolean online = getBoolean("online-mode", true);
		if(online) {
			logger.warning("* ! * ! * ! * ! * ! * ! * ! * ! * ! * !");
			logger.warning("Online mode is currently unavailable!");
			set("online-mode", false);
			online = false;
		}
		return online;
	}
	
	int getDifficulty() {
		int diff = getInt("difficulty", 1);
		if(diff < 1)
			diff = 1;
		else if (diff > 3)
			diff = 3;
		return diff;
	}
	
	int getGamemode() {
		int gm = getInt("gamemode", 0);
		if(gm > 2) // FIXME Spectator mode in MC 1.8.
			gm = 2;
		return gm;
	}

	void generateNew() {
		logger.info("Generating new properties file");
		save();
	}

	void save() {
		FileWriter writer = null;

		try {
			writer = new FileWriter(file);
			prop.store(writer, "Minecraft server properties");

		} catch (Exception ex) {
			logger.log(Level.WARNING, "Failed to save " + file, ex);
			generateNew();

		}
	}
	
	void set(String path, Object def) {
		prop.setProperty(path, "" + String.valueOf(def));
	}

	String getString(String path, String def) {
		if (!prop.containsKey(path)) {
			prop.setProperty(path, def);
			save();
		}

		return prop.getProperty(path, def);
	}

	int getInt(String path, int def) {
		try {
			return Integer.valueOf(getString(path, "" + def));
		} catch (NumberFormatException ex) {
			prop.setProperty(path, String.valueOf(def));
			return def;
		}
	}

	boolean getBoolean(String path, boolean def) {
		try {
			return Boolean.valueOf(getString(path, "" + def));
		} catch (Exception ex) {
			prop.setProperty(path, String.valueOf(def));
			return def;
		}
	}
}

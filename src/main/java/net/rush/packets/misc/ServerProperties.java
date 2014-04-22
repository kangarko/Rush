package net.rush.packets.misc;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerProperties {

	private final Properties prop = new Properties();
	private final Logger logger = Logger.getLogger("Minecraft");
	private final File file;

	public ServerProperties(String fileName) {
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
	
	public void set(String path, Object def) {
		prop.setProperty(path, "" + String.valueOf(def));
	}

	public String getString(String path, String def) {
		if (!prop.containsKey(path)) {
			prop.setProperty(path, def);
			save();
		}

		return prop.getProperty(path, def);
	}

	public int getInt(String path, int def) {
		try {
			return Integer.valueOf(getString(path, "" + def));
		} catch (NumberFormatException ex) {
			prop.setProperty(path, String.valueOf(def));
			return def;
		}
	}

	public boolean getBoolean(String path, boolean def) {
		try {
			return Boolean.valueOf(getString(path, "" + def));
		} catch (Exception ex) {
			prop.setProperty(path, String.valueOf(def));
			return def;
		}
	}
}

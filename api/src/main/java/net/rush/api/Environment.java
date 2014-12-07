package net.rush.api;

import java.util.HashMap;

/**
 * Represents various map environment types that a world may be
 */
public enum Environment {
	/**
	 * Represents the "normal"/"surface world" map
	 */
	NORMAL(0),
	/**
	 * Represents a nether based map ("hell")
	 */
	NETHER(-1),
	/**
	 * Represents the "end" map
	 */
	THE_END(1);

	private final int id;
	private static final HashMap<Integer, Environment> BY_ID = new HashMap<>();

	private Environment(int id) {
		this.id = id;
	}

	/**
	 * Gets the dimension ID of this environment
	 *
	 * @return dimension ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get an environment by ID
	 *
	 * @param id The ID of the environment
	 * @return The environment
	 */
	public static Environment getEnvironment(int id) {
		return BY_ID.get(id);
	}

	static {
		for (Environment env : values())
			BY_ID.put(env.getId(), env);
	}
}
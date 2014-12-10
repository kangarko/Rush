package net.rush.api;

import java.util.HashMap;

/**
 * Represents the various types of game modes that Player may
 * have
 */
public enum GameMode {
    /**
     * Creative mode may fly, build instantly, become invulnerable and create
     * free items.
     */
    CREATIVE(1),

    /**
     * Survival mode is the "normal" gameplay type, with no special features.
     */
    SURVIVAL(0),

    /**
     * Adventure mode cannot break blocks without the correct tools.
     */
    ADVENTURE(2);

    private final int value;
    private final static HashMap<Integer, GameMode> BY_ID = new HashMap<>();

    private GameMode(final int value) {
        this.value = value;
    }

    /**
     * Gets the mode value associated with this GameMode
     *
     * @return An integer value of this gamemode
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets the GameMode represented by the specified value
     *
     * @param value Value to check
     * @return Associative {@link GameMode} with the given value, or null if
     *     it doesn't exist
     */
    public static GameMode getByValue(int value) {
        return BY_ID.get(value);
    }

    static {
        for (GameMode mode : values())
            BY_ID.put(mode.getValue(), mode);
    }
}

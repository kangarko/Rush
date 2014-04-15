package net.rush.util;

import org.bukkit.ChatColor;


public final class StringUtils {
	
	public static String join(String[] items, String glue) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < items.length; i++) {
			builder.append(items[i]);
			if (i != items.length - 1) {
				builder.append(glue);
			}
		}
		return builder.toString();
	}
	
	/**
	 * Colorizes the string. Replaces & with color character.
	 * @param str - The string to be colorized.
	 * @returns colorized string
	 */
	public static String colorize(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	
	private StringUtils() {}

}


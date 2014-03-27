package net.rush.util;


public final class StringUtils {

	/**
	 * This is fall back if somehow the character get corrupted
	 * for example in github so I do not need to check every class
	 * and fix it.
	 */
	public final static String COLOR_CHAR = "§";
	
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
	 * Colorizes the string. Replaces & with special character.
	 * @param str - The string to be colorized.
	 * @returns colorized string
	 */
	public static String c(String str) {
		return str.replace("&", COLOR_CHAR);
	}
	
	private StringUtils() {
	}

}


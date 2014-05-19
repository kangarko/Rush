package net.rush.util;

import java.text.DecimalFormat;

public final class StringUtils {
	
	private static DecimalFormat twoDecFormat = new DecimalFormat("#.##");
	
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
		return str.replace('&', '\u00A7');
	}
	
	public static String serializeLoc(Number x, Number y, Number z) {
		if(x instanceof Double || x instanceof Float)
			x = Double.valueOf(twoDecFormat.format(x));
		if(y instanceof Double || y instanceof Float)
			y = Double.valueOf(twoDecFormat.format(y));
		if(z instanceof Double || z instanceof Float)
			z = Double.valueOf(twoDecFormat.format(z));
		
		return "x: " + x + ", y: " + y + ", z: " + z;
	}
	
	private StringUtils() {}

}


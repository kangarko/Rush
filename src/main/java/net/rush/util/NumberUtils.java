package net.rush.util;

import java.text.DecimalFormat;

public class NumberUtils {

	/**
	 * No need to initialize this class.
	 */
	private NumberUtils() {}
	
	
	/**
	 * Formats given milliseconds to seconds with 3 decimal places like this
	 * @param milliseconds the milliseconds
	 * @return the formatted value as string like this: #.###
	 */
	public static String msToSeconds(long milliseconds) {
		double seconds = ((double)milliseconds / 1000);
		DecimalFormat df = new DecimalFormat("#.###");
        return df.format(seconds);
	}
}

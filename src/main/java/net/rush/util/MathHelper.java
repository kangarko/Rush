package net.rush.util;

import java.util.Random;

public class MathHelper {
	
	/**
	 * A table of sin values computed from 0 (inclusive) to 2*pi (exclusive), with steps of 2*PI / 65536.
	 */
	private static float[] SIN_TABLE = new float[65536];

	/**
	 * sin looked up in a table
	 */
	public static final float sin(float param) {
		return SIN_TABLE[(int) (param * 10430.378F) & 65535];
	}

	/**
	 * cos looked up in the sin table with the appropriate offset
	 */
	public static final float cos(float param) {
		return SIN_TABLE[(int) (param * 10430.378F + 16384.0F) & 65535];
	}

	public static final float sqrt_float(float param) {
		return (float) Math.sqrt((double) param);
	}

	public static final float sqrt_double(double param) {
		return (float) Math.sqrt(param);
	}

	/**
	 * Returns the greatest integer less than or equal to the float argument
	 */
	public static int floor_float(float param) {
		int var1 = (int) param;
		return param < (float) var1 ? var1 - 1 : var1;
	}

	/**
	 * Returns the greatest integer less than or equal to the double argument
	 */
	public static int floor_double(double param) {
		int var2 = (int) param;
		return param < (double) var2 ? var2 - 1 : var2;
	}

	/**
	 * Long version of floor_double
	 */
	public static long floor_double_long(double param) {
		long longParam = (long) param;
		return param < (double) longParam ? longParam - 1L : longParam;
	}

	public static float abs(float param) {
		return param >= 0.0F ? param : -param;
	}

	/**
	 * Returns the unsigned value of an int.
	 */
	public static int abs_int(int param) {
		return param >= 0 ? param : -param;
	}

	public static int ceiling_float_int(float param) {
		int var1 = (int) param;
		return param > (float) var1 ? var1 + 1 : var1;
	}

	public static int ceiling_double_int(double param) {
		int var2 = (int) param;
		return param > (double) var2 ? var2 + 1 : var2;
	}

	/**
	 * Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and
	 * third parameters.
	 */
	public static int clamp_int(int first, int second, int third) {
		return first < second ? second : (first > third ? third : first);
	}

	/**
	 * Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and
	 * third parameters
	 */
	public static float clamp_float(float first, float second, float third) {
		return first < second ? second : (first > third ? third : first);
	}

	/**
	 * Maximum of the absolute value of two numbers.
	 */
	public static double abs_max(double first, double second) {
		if (first < 0.0D) {
			first = -first;
		}

		if (second < 0.0D) {
			second = -second;
		}

		return first > second ? first : second;
	}

	public static int getRandomIntegerInRange(Random rand, int lowest, int highest) {
		return lowest >= highest ? lowest : rand.nextInt(highest - lowest + 1) + lowest;
	}

	public static double getRandomDoubleInRange(Random rand, double lowest, double highest) {
		return lowest >= highest ? lowest : rand.nextDouble() * (highest - lowest) + lowest;
	}

	public static double average(long[] longParams) {
		long var1 = 0L;
		long[] var3 = longParams;
		int var4 = longParams.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			long var6 = var3[var5];
			var1 += var6;
		}

		return (double) var1 / (double) longParams.length;
	}

	/**
	 * the angle is reduced to an angle between -180 and +180 by mod, and a 360 check
	 */
	public static float wrapAngleTo180_float(float param) {
		param %= 360.0F;

		if (param >= 180.0F) {
			param -= 360.0F;
		}

		if (param < -180.0F) {
			param += 360.0F;
		}

		return param;
	}

	/**
	 * the angle is reduced to an angle between -180 and +180 by mod, and a 360 check
	 */
	public static double wrapAngleTo180_double(double param) {
		param %= 360.0D;

		if (param >= 180.0D) {
			param -= 360.0D;
		}

		if (param < -180.0D) {
			param += 360.0D;
		}

		return param;
	}

	/**
	 * parses the string as integer or returns the second parameter if it fails
	 */
	public static int parseIntWithDefault(String primary, int secondary) {
		int output = secondary;

		try {
			output = Integer.parseInt(primary);
		} catch (Throwable var4) {
			;
		}

		return output;
	}

	/**
	 * parses the string as integer or returns the second parameter if it fails. this value is capped to par2
	 */
	public static int parseIntWithDefaultAndMax(String primary, int param1, int param2) {
		int output = param1;

		try {
			output = Integer.parseInt(primary);
		} catch (Throwable var5) {
			;
		}

		if (output < param2) {
			output = param2;
		}

		return output;
	}

	/**
	 * parses the string as double or returns the second parameter if it fails.
	 */
	public static double parseDoubleWithDefault(String primary, double secondary) {
		double output = secondary;

		try {
			output = Double.parseDouble(primary);
		} catch (Throwable var6) {
			;
		}

		return output;
	}

	public static double func_82713_a(String primary, double param1, double param2) {
		double output = param1;

		try {
			output = Double.parseDouble(primary);
		} catch (Throwable var8) {
			;
		}

		if (output < param2) {
			output = param2;
		}

		return output;
	}

	static {
		for (int index = 0; index < 65536; ++index) {
			SIN_TABLE[index] = (float) Math.sin((double) index * Math.PI * 2.0D / 65536.0D);
		}
	}
}

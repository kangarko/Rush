package net.rush.api;

public class MathHelper {

	public static byte floatToByte(float old) {
		return (byte) (int) ((old * 256F) / 360F);
	}

	public static float byteToFloat(byte old) {
		return (old * 360) / 256F;
	}
}

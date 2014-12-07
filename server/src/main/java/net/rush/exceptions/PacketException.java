package net.rush.exceptions;

public class PacketException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PacketException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public PacketException(String message) {
		super(message);
	}
}

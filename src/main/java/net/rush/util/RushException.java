package net.rush.util;

public class RushException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RushException(String str) {
		super(str);
	}

	public RushException(Throwable ex) {
		super(ex);
	}
	
	public RushException(String str, Throwable ex) {
		super(str, ex);
	}
}

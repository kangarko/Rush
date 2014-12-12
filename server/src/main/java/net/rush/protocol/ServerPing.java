package net.rush.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ServerPing {

	private final Protocol protocol;
	private String description;
	private final String favicon;
	private final Players players;

	@Data
	public static class Protocol {
		private final String name;
		private final int protocol;
	}

	@Data
	public static class Players {
		private final int maximum;
		private final int online;
	}
}
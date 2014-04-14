package net.rush.packets.serialization;

final class SerializationInfo {
	private final Serialize serialize;
	private final String name;

	SerializationInfo(Serialize serialize, String name) {
		this.serialize = serialize;
		this.name = name;
	}

	public Serialize getSerialize() {
		return serialize;
	}

	public String getName() {
		return name;
	}
}

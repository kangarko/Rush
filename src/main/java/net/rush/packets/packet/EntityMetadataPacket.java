package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.util.Parameter;

public class EntityMetadataPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int entityId;
	@Serialize(type = Type.ENTITY_METADATA, order = 1)
	private final Parameter<?>[] metadata;

	public EntityMetadataPacket(int entityId, Parameter<?>[] metadata) {
		super();
		this.entityId = entityId;
		this.metadata = metadata;
	}

	public int getOpcode() {
		return 0x28;
	}

	public int getEntityId() {
		return entityId;
	}

	public Parameter<?>[] getMetadata() {
		return metadata;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",metadata=\"%s\"", entityId, metadata);
	}
}

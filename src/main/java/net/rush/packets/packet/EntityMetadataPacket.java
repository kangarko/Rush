package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.util.Parameter;

public class EntityMetadataPacket extends Packet {

	public EntityMetadataPacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.ENTITY_METADATA, order = 1)
	private Parameter<?>[] metadata;

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

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		entityId = input.readInt();
		metadata = readMetadata(input);
	}

	@Override
	public void write17(ByteBufOutputStream out) throws IOException {
		out.writeInt(entityId);
		writeMetadata(out, metadata);
	}
}

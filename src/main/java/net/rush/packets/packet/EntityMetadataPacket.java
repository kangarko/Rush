package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.model.Coordinate;
import net.rush.model.ItemStack;
import net.rush.packets.Packet;
import net.rush.packets.misc.MetadataType;
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

		Parameter<?>[] parameters = new Parameter<?>[Parameter.METADATA_SIZE];
		for (int x = input.readUnsignedByte(); x != 127; x = input.readUnsignedByte()) {
			int index = x & 0x1F;
			int type = x >> 5;
			MetadataType metaType = MetadataType.fromId(type);
			switch (metaType) {
				case BYTE:
					parameters[index] = new Parameter<Byte>(type, index, input.readByte());
					break;
				case SHORT:
					parameters[index] = new Parameter<Short>(type, index, input.readShort());
					break;
				case INT:
					parameters[index] = new Parameter<Integer>(type, index, input.readInt());
					break;
				case FLOAT:
					parameters[index] = new Parameter<Float>(type, index, input.readFloat());
					break;
				case STRING:
					parameters[index] = new Parameter<String>(type, index, Type.readUtf8String(input));
					break;
				case ITEM:
					short id = input.readShort();
					if (id <= 0) {
						parameters[index] = new Parameter<ItemStack>(type, index, ItemStack.NULL_ITEM);
					} else {
						byte stackSize = input.readByte();
						short dataValue = input.readShort();
						//short dataLenght = input.readShort();
						//byte[] metadata = new byte[0];
						//if(dataLenght > 0) {
						// FIXME previous check if its enchantable
						//metadata = new byte[dataLenght];
						//input.readFully(metadata);
						//}
						parameters[index] = new Parameter<ItemStack>(type, index, new ItemStack(id, stackSize, dataValue));
					}
					break;
				default:
					throw new UnsupportedOperationException("Metadata-type '" + metaType + "' is not implemented!");
			}
		}
		metadata = parameters;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write17(ByteBufOutputStream out) throws IOException {
		out.writeInt(entityId);

		for (Parameter<?> parameter : metadata) {

			if (parameter == null)
				continue;

			int type = parameter.getType();
			int index = parameter.getIndex();

			out.writeByte(((type & 0x07) << 5) | (index & 0x1F));

			switch (type) {
				case Parameter.TYPE_BYTE:
					out.writeByte(((Parameter<Byte>) parameter).getValue());
					break;
				case Parameter.TYPE_SHORT:
					out.writeShort(((Parameter<Short>) parameter).getValue());
					break;
				case Parameter.TYPE_INT:
					out.writeInt(((Parameter<Integer>) parameter).getValue());
					break;
				case Parameter.TYPE_FLOAT:
					out.writeFloat(((Parameter<Float>) parameter).getValue());
					break;
				case Parameter.TYPE_STRING:
					Type.writeUtf8String(out, ((Parameter<String>) parameter).getValue());
					break;
				case Parameter.TYPE_ITEM:
					ItemStack item = ((Parameter<ItemStack>) parameter).getValue();

					if (item.getId() <= 0) { // FIXME less then zero check
						out.writeShort(-1);
					} else {
						out.writeShort(item.getId());
						out.writeByte(item.getCount());
						out.writeShort(item.getDamage());
						out.writeShort(-1);
						//if (item.getDataLength() >= 0) { // FIXME previous check if its enchantable
						//	out.write(item.getMetadata());
						//}
					}
					break;
				case Parameter.TYPE_COORDINATE:
					Coordinate coord = ((Parameter<Coordinate>) parameter).getValue();
					out.writeInt(coord.getX());
					out.writeInt(coord.getY());
					out.writeInt(coord.getZ());
					break;
			}
		}
		out.writeByte(127);
	}
}

package net.rush.packets.packet;

import net.rush.model.Position;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.util.Parameter;

public class NamedEntitySpawnPacket extends Packet {
	public NamedEntitySpawnPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.STRING, order = 1)
	private String entityName;
	@Serialize(type = Type.INT, order = 2)
	private int x;
	@Serialize(type = Type.INT, order = 3)
	private int y;
	@Serialize(type = Type.INT, order = 4)
	private int z;
	@Serialize(type = Type.BYTE, order = 5)
	private byte yaw;
	@Serialize(type = Type.BYTE, order = 6)
	private byte pitch;
	@Serialize(type = Type.SHORT, order = 7)
	private short currentItem;
	@Serialize(type = Type.ENTITY_METADATA, order = 8)
	private Parameter<?>[] metadata;

	public NamedEntitySpawnPacket(int entityId, String playerName, Position pos, byte yaw, byte pitch, short currentItem, Parameter<?>[] metadata) {
		super();
		this.entityId = entityId;
		entityName = playerName;
		x = (int) pos.getX();
		y = (int) pos.getY();
		z = (int) pos.getZ();
		this.yaw = yaw;
		this.pitch = pitch;
		this.currentItem = currentItem;
		this.metadata = metadata;
	}

	public int getOpcode() {
		return 0x14;
	}

	public int getEntityId() {
		return entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public byte getYaw() {
		return yaw;
	}

	public byte getPitch() {
		return pitch;
	}

	public short getCurrentItem() {
		return currentItem;
	}

	public Parameter<?>[] getMetadata() {
		return metadata;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",playerName=\"%s\",x=\"%d\",y=\"%d\",z=\"%d\",yaw=\"%d\",pitch=\"%d\",currentItem=\"%d\"", entityId, entityName, x, y, z, yaw, pitch, currentItem);
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		writeVarInt(entityId, output);
		writeString("0-0-0-0-0", output, false);
		writeString(entityName, output, false);
		output.writeInt(x);
		output.writeInt(y);
		output.writeInt(z);
		output.writeByte(yaw);
		output.writeByte(pitch);
		output.writeShort(currentItem);

		for (Parameter<?> parameter : metadata) {

			if (parameter == null)
				continue;

			int type = parameter.getType();
			int index = parameter.getIndex();

			output.writeByte(((type & 0x07) << 5) | (index & 0x1F));

			switch (type) {
				case Parameter.TYPE_BYTE:
					output.writeByte(((Parameter<Byte>) parameter).getValue());
					break;
				case Parameter.TYPE_SHORT:
					output.writeShort(((Parameter<Short>) parameter).getValue());
					break;
				case Parameter.TYPE_INT:
					output.writeInt(((Parameter<Integer>) parameter).getValue());
					break;
				case Parameter.TYPE_FLOAT:
					output.writeFloat(((Parameter<Float>) parameter).getValue());
					break;
				case Parameter.TYPE_STRING:
					Type.writeUtf8String(output, ((Parameter<String>) parameter).getValue());
					break;
				case Parameter.TYPE_ITEM:
					ItemStack item = ((Parameter<ItemStack>) parameter).getValue();

					if (item.getId() <= 0) { // FIXME less then zero check
						output.writeShort(-1);
					} else {
						output.writeShort(item.getId());
						output.writeByte(item.getCount());
						output.writeShort(item.getDamage());
						output.writeShort(-1);
						//if (item.getDataLength() >= 0) { // FIXME previous check if its enchantable
						//	out.write(item.getMetadata());
						//}
					}
					break;
				case Parameter.TYPE_COORDINATE:
					Coordinate coord = ((Parameter<Coordinate>) parameter).getValue();
					output.writeInt(coord.getX());
					output.writeInt(coord.getY());
					output.writeInt(coord.getZ());
					break;
			}
		}
		output.writeByte(127);
	}*/
}

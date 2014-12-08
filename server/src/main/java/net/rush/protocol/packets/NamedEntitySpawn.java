package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.api.Position;
import net.rush.api.meta.MetaParam;
import net.rush.protocol.Packet;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class NamedEntitySpawn extends Packet {

	private int entityId;
	private String entityName;
	private int x;
	private int y;
	private int z;
	private int yaw;
	private int pitch;
	private int currentItem;
	private MetaParam<?>[] metadata;

	public NamedEntitySpawn(int entityId, String playerName, Position pos, int currentItem, MetaParam<?>[] metadata) {
		super();
		this.entityId = entityId;
		this.entityName = playerName;
		this.x = pos.getPixelX();
		this.y = pos.getPixelY();
		this.z = pos.getPixelZ();
		this.yaw = pos.getIntYaw();
		this.pitch = pos.getIntPitch();
		this.currentItem = currentItem;
		this.metadata = metadata;
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityId, out);

		writeString("0-0-0-0-0", out);
		writeString(entityName, out);
		
		//if (protocol > 4) // TODO Support both 1.7.2 and 1.7.10 ?
		//	writeVarInt(0, out);
		
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
		out.writeByte(yaw);
		out.writeByte(pitch);
		out.writeShort(currentItem);
		writeMetadata(out, metadata);
	}
}

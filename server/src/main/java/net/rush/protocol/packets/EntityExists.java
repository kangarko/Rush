package net.rush.protocol.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public abstract class EntityExists extends Packet {

	private int entityId;
	
	protected int x;
	protected int y;
	protected int z;
	
	protected int yaw;
	protected int pitch;

	protected EntityExists(int entityId, int x, int y, int z, int yaw, int pitch) {
		this.entityId = entityId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	protected EntityExists(int entityId, int x, int y, int z) {
		this(entityId, x, y, z, 0, 0);
	}
	
	protected EntityExists(int entityId, int yaw, int pitch) {
		this(entityId, 0, 0, 0, yaw, pitch);
	}
	
	protected EntityExists(int entityId) {
		this(entityId, 0, 0, 0, 0, 0);
	}
	
	@Override
	public void read(ByteBuf in) throws IOException {
		entityId = in.readInt();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeInt(entityId);
	}
}

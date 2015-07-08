package net.rush.protocol.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EntityHeadLook extends EntityExists {

	public EntityHeadLook(int entityId, int yaw) {
		super(entityId, yaw, 0);
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		super.read(in);
		
		yaw = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		super.write(out);
		
		out.writeByte(yaw);
	}
}

package net.rush.protocol.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EntityLook extends EntityExists {

	public EntityLook(int entityId, int yaw, int pitch) {
		super(entityId, yaw, pitch);
	}
	
	@Override
	public void read(ByteBuf in) throws IOException {
		super.read(in);
		
		yaw = in.readByte();
		pitch = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		super.write(out);
		
		out.writeByte(yaw);
		out.writeByte(pitch);
	}

}

package net.rush.protocol.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EntityRelMove extends EntityExists {

	public EntityRelMove(int entityId, int x, int y, int z) {
		super(entityId, x, y, z);
	}


	@Override
	public void write(ByteBuf out) throws IOException {
		super.write(out);
		
		out.writeByte(x);
		out.writeByte(y);
		out.writeByte(z);
	}
}

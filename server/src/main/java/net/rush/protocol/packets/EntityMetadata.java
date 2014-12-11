package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.api.meta.MetaParam;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EntityMetadata extends EntityExists {

	private MetaParam<?>[] metadata;
	
	public EntityMetadata(int entityId, MetaParam<?>[] metadata) {
		super(entityId);
		
		this.metadata = metadata;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		super.read(in);

		metadata = readMetadata(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		super.write(out);

		writeMetadata(out, metadata);
	}
}

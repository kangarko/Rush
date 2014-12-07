package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DestroyEntity extends Packet {

	private int[] entityIDs;

	public DestroyEntity(int... entityIDs) {
		super();
		this.entityIDs = entityIDs;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		entityIDs = new int[in.readByte()];

		for (int i = 0; i < entityIDs.length; ++i)
			entityIDs[i] = in.readInt();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(entityIDs.length);

		for (int i = 0; i < entityIDs.length; ++i)
			out.writeInt(entityIDs[i]);
	}
}

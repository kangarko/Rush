package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ChunkBulk extends Packet {

	private int x;
	private int z;
	private boolean groundUpContinuous;
	private int primaryBitMap;
	private int addBitMap;
	private byte[] chunkData;
	

	@Override
	public void write(ByteBuf output) throws IOException {
		output.writeInt(x);
		output.writeInt(z);
		output.writeBoolean(groundUpContinuous);
		output.writeShort(primaryBitMap);
		output.writeShort(addBitMap);
		output.writeInt(chunkData.length);
		output.writeBytes(chunkData);
	}
}

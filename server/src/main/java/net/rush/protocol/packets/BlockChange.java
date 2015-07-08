package net.rush.protocol.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class BlockChange extends Packet {

	private int x;
	private int y;
	private int z;
	private int id;
	private int data;

	@Override
	public void write(ByteBuf out) throws IOException {
		writePosYByte(x, y, z, out);
		writeVarInt(id, out);
		out.writeByte(data);
	}
}

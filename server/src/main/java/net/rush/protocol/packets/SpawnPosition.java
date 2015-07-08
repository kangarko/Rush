package net.rush.protocol.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.api.Position;
import net.rush.protocol.Packet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class SpawnPosition extends Packet {

	private int x;
	private int y;
	private int z;

	public SpawnPosition(Position pos) {
		super();
		x = (int) pos.x;
		y = (int) pos.y;
		z = (int) pos.z;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		x = in.readInt();
		y = in.readInt();
		z = in.readInt();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writePosIntegers(x, y, z, out);
	}
}

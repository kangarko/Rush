package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Digging extends Packet {

	public static enum DiggingStatus {
		START(0),
		CANCEL(1),
		DONE(2),
		DROP_ITEMSTACK(3),
		DROP_ITEM(4),
		SHOOT_OR_EAT(5);

		@Getter
		private int id;
		
		DiggingStatus(int id) {
			this.id = id;
		}
		
		public static DiggingStatus fromId(int id) {
			for (DiggingStatus st : values())
				if(st.getId() == id)
					return st;
			
			throw new NullPointerException("Unknown digging status ID " + id);
		}
	}

	private DiggingStatus status;
	private int x;
	private int y;
	private int z;
	private int face;

	@Override
	public void read(ByteBuf input) throws IOException {
		status = DiggingStatus.fromId(input.readByte());
		x = input.readInt();
		y = input.readUnsignedByte();
		z = input.readInt();
		face = input.readByte();
	}
}

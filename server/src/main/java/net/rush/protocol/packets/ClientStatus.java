package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ClientStatus extends Packet {

	public static enum Action {
		PERFORM_RESPAWN(0),
		REQUEST_STATS(1),
		OPEN_ACHIEVEMENT_INVENTORY(2);

		int id;

		private Action(int id) {
			this.id = id;
		}

		public static Action byId(int id) {
			Action action = values()[id];
			Objects.requireNonNull(action, "Unknown client status action id " + id);

			return action;
		}
	}

	private Action action;

	@Override
	public void read(ByteBuf in) throws IOException {
		action = Action.byId(in.readByte());
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(action.id);
	}
}

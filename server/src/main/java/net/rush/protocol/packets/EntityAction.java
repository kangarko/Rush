package net.rush.protocol.packets;

import java.io.IOException;
import java.util.Objects;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EntityAction extends EntityExists {

	public static enum Action {
		ACTION_CROUCH(1),
		UNCROUCH(2),
		LEAVE_BED(3),
		START_SPRINTING(4),
		STOP_SPRINTING(5);

		public int id;
		Action(int id) {
			this.id = id;
		}

		public static Action fromId(int id) {
			Action action = values()[id - 1];
			Objects.requireNonNull(action, "Unknown entity action id " + id);

			return action;
		}
	}

	private Action action;
	private int horseJumpBoost;

	public EntityAction(int entityId, Action action) {
		this(entityId, action, 0);
	}
	
	public EntityAction(int entityId, Action action, int horseJumpBoost) {
		super(entityId);
		
		this.action = action;
		this.horseJumpBoost = horseJumpBoost;
	}
	
	@Override
	public void read(ByteBuf in) throws IOException {
		super.read(in);
		
		action = Action.fromId(in.readByte());
		horseJumpBoost = in.readInt();
	}
}

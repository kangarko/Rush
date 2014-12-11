package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Animation extends Packet {

	public static enum Type {
		SWING_ARM(1),
		DAMAGE_ANIMATION(2),
		BED_LEAVE(3),
		EAT_FOOD(5),
		CRITICAL_EFFECT(6),
		MAGIC_CRITICAL_EFFECT(7),
		UNKNOWN(102),
		CROUNCH(104),
		UNCROUNCH(105);

		private final static HashMap<Integer, Type> BY_ID = new HashMap<>();
		public final int id;
		
		Type(int id) {
			this.id = id;
		}

	    public static Type getById(int value) {
	        return BY_ID.get(value);
	    }

	    static {
	        for (Type diff : values())
	            BY_ID.put(diff.id, diff);
	    }
	}

	private int entityId;
	private Type animation;

	@Override
	public void read(ByteBuf in) throws IOException {
		entityId = in.readInt();
		animation = Type.getById(in.readByte());
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityId, out);
		out.writeByte(toNewId(animation.id));
	}

	private int toNewId(int id) {
		if(id == 2)
			return 1;
		if(id == 1)
			return 0;
		return id-= 2;
	}
}

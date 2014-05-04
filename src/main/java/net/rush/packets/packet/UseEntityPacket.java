package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class UseEntityPacket extends Packet {
	
	public UseEntityPacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int playerEntityId;
	@Serialize(type = Type.INT, order = 1)
	private int targetEntityId;
	@Serialize(type = Type.BOOL, order = 2)
	private boolean rightclick;

	public UseEntityPacket(int playerEntityId, int targetEntityId, boolean rightclick) {
		super();
		this.playerEntityId = playerEntityId;
		this.targetEntityId = targetEntityId;
		this.rightclick = rightclick;
	}

	public int getOpcode() {
		return 0x07;
	}

	public int getPlayerEntityId() {
		return playerEntityId;
	}

	public int getTargetEntityId() {
		return targetEntityId;
	}

	public boolean getRightclick() {
		return rightclick;
	}

	public String getToStringDescription() {
		return String.format("playerEntityId=\"%d\",targetEntityId=\"%d\",rightclick=\"%b\"", playerEntityId, targetEntityId, rightclick);
	}
	
	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		targetEntityId = input.readInt();
		rightclick = input.readByte() == 0;
	}

}

package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.util.RushException;
import net.rush.util.enums.GameStateReason;

public class ChangeGameStatePacket extends Packet {
	
	public ChangeGameStatePacket() {
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte reason;
	@Serialize(type = Type.BYTE, order = 1)
	private byte value;

	public ChangeGameStatePacket(GameStateReason reason, int value) {
		super();
		this.reason = (byte) reason.getValue();
		this.value = (byte) value;
	}
	
	public ChangeGameStatePacket(GameStateReason reason) {
		super();
		
		if(reason.needsMoreInfo())
			throw new RushException("GameStateReason: " + reason.name() + " needs additional info!");
		
		this.reason = (byte) reason.getValue();
		this.value = 0;
	}

	public int getOpcode() {
		return 0x46;
	}

	public byte getReason() {
		return reason;
	}

	public byte getValue() {
		return value;
	}

	public String getToStringDescription() {
		return String.format("reason=\"%d\",value=\"%d\"", reason, value);
	}
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeByte(reason);
		output.writeFloat(value);
	}

}

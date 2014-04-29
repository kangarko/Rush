package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ConfirmTransactionPacket extends Packet {
	public ConfirmTransactionPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte windowId;
	@Serialize(type = Type.SHORT, order = 1)
	private short action;
	@Serialize(type = Type.BOOL, order = 2)
	private boolean accepted;

	public ConfirmTransactionPacket(byte windowId, short action,
			boolean accepted) {
		super();
		this.windowId = windowId;
		this.action = action;
		this.accepted = accepted;
	}

	public int getOpcode() {
		return 0x6A;
	}

	public byte getWindowId() {
		return windowId;
	}

	public short getAction() {
		return action;
	}

	public boolean getAccepted() {
		return accepted;
	}

	public String getToStringDescription() {
		return String.format("windowId=\"%d\",action=\"%d\",accepted=\"%b\"",
				windowId, action, accepted);
	}

	@Override
	public void read17(ByteBufInputStream input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write17(ByteBufOutputStream output) {
		// TODO Auto-generated method stub

	}
}

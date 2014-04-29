package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class IncrementStatisticPacket extends Packet {
	public IncrementStatisticPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.INT, order = 0)
	private int statisticId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte amount;

	public IncrementStatisticPacket(int statisticId, byte amount) {
		super();
		this.statisticId = statisticId;
		this.amount = amount;
	}

	public int getOpcode() {
		return 0xC8;
	}

	public int getStatisticId() {
		return statisticId;
	}

	public byte getAmount() {
		return amount;
	}

	public String getToStringDescription() {
		return String.format("statisticId=\"%d\",amount=\"%d\"", statisticId,
				amount);
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

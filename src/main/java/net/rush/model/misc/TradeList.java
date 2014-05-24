package net.rush.model.misc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.rush.model.ItemStack;
import net.rush.packets.Packet;

public class TradeList extends ArrayList<Trade> {

	private static final long serialVersionUID = 1L;

	public void writeRecipesToStream(DataOutputStream output) throws IOException {
		output.writeByte(size() & 255);

		for (Trade trade : this) {
			
			Packet.writeItemstack(trade.getBuying(), output);
			Packet.writeItemstack(trade.getSelling(), output);
			
			ItemStack secondItem = trade.getSecondBuying();
			
			output.writeBoolean(secondItem != null);

			if (secondItem != null)
				Packet.writeItemstack(secondItem, output);

			output.writeBoolean(trade.isLocked());
		}
	}

}

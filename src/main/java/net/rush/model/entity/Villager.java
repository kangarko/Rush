package net.rush.model.entity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.rush.model.Block;
import net.rush.model.EntityAgeable;
import net.rush.model.Item;
import net.rush.model.ItemStack;
import net.rush.model.Player;
import net.rush.model.misc.Trade;
import net.rush.model.misc.TradeList;
import net.rush.packets.packet.PluginMessagePacket;
import net.rush.util.Parameter;
import net.rush.util.enums.InventoryEnum;
import net.rush.util.enums.VillagerType;
import net.rush.world.World;

import org.bukkit.entity.EntityType;

public class Villager extends EntityAgeable {
	
	private TradeList trades = new TradeList();
	
	public Villager(World world) {
		super(world, EntityType.VILLAGER);
	
		// TODO debug
		trades.add(new Trade(new ItemStack(Item.BOWL.id, 2), new ItemStack(Block.WOOD.id)));
		trades.add(new Trade(Item.FEATHER.id, Item.GOLD_INGOT.id, Item.GOLD_BARDING.id));
		trades.add(new Trade(Item.FEATHER.id, Item.DIAMOND.id, Item.DIAMOND_BARDING.id));
	}
	
	@Override
	public void onPlayerInteract(Player pl) {
		pl.openInventory(InventoryEnum.VILLAGER_TRADE);

		if (!trades.isEmpty()) {
			try {
				ByteArrayOutputStream arrayOutput = new ByteArrayOutputStream();
				DataOutputStream output = new DataOutputStream(arrayOutput);
				
				output.writeInt(pl.windowId);
				trades.writeRecipesToStream(output);
				
				pl.getSession().send(new PluginMessagePacket("MC|TrList", arrayOutput.toByteArray()));
				
				arrayOutput.flush();
				arrayOutput.close();

				output.flush();
				output.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	@Override
	public void onPlayerHit(Player pl) {
		super.onPlayerHit(pl);
		
		pl.sendMessage("&6[AngryVillager ID" + id + "] &cYou hit me I'll f*#$ you up!");
		pl.setHealth(pl.getHealth() - 1);
	}
	
	// METADATA START
	
	public void setVillagerType(VillagerType type) {
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 16, (byte)type.getType()));
	}
	
	public byte getVillagerType() {
		return ((Byte)getMetadata(16).getValue());
	}
	
	// METADATA END
}

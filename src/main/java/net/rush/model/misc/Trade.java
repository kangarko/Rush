package net.rush.model.misc;

import net.rush.model.ItemStack;

public class Trade {

	private ItemStack buying;
	private ItemStack secondBuy;
	private ItemStack selling;

	private int uses = 0;
	private int maxUses;

	public Trade(int buy, int sell) {
		this(new ItemStack(buy), null, new ItemStack(sell));
	}
	
	public Trade(int buy, int secondBuy, int sell) {
		this(new ItemStack(buy), new ItemStack(secondBuy), new ItemStack(sell));
	}
	
	public Trade(ItemStack buy, ItemStack sell) {
		this(buy, null, sell);
	}
	
	public Trade(ItemStack buy, ItemStack secondBuy, ItemStack sell) {
		this.buying = buy;
		this.secondBuy = secondBuy;
		this.selling = sell;
		
		this.maxUses = 7;
	}

	public boolean hasSecondBuy() {
		return secondBuy != null;
	}

	public boolean hasSameIDsAs(Trade merchant) {
		return buying.id == merchant.buying.id && selling.id == merchant.selling.id ? secondBuy == null && merchant.secondBuy == null
				|| secondBuy != null && merchant.secondBuy != null && secondBuy.id == merchant.secondBuy.id : false;
	}

	public boolean hasSameItemsAs(Trade merchant) {
		return hasSameIDsAs(merchant) && (buying.count < merchant.buying.count || secondBuy != null && secondBuy.count < merchant.secondBuy.count);
	}
	
	public ItemStack getBuying() {
		return buying;
	}
	
	public ItemStack getSecondBuying() {
		return secondBuy;
	}
	
	public ItemStack getSelling() {
		return selling;
	}

	public int getMaxTradeUses() {
		return maxUses;
	}
	
	public void incrementUses() {
		uses++;
	}
	
	public boolean isLocked() {
		return uses >= maxUses;
	}
}

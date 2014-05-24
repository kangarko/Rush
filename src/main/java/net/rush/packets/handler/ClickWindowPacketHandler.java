package net.rush.packets.handler;

import java.util.logging.Level;

import net.rush.inventory.PlayerInventory;
import net.rush.model.ItemStack;
import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.ClickWindowPacket;
import net.rush.packets.packet.ConfirmTransactionPacket;

import org.bukkit.GameMode;

public class ClickWindowPacketHandler extends PacketHandler<ClickWindowPacket> {

	@Override
    public void handle(Session session, Player player, ClickWindowPacket message) {
        if (player == null)
            return;
        
        PlayerInventory inv = player.getInventory();
        
        if (message.getSlot() == -1 || message.getSlot() == -999) {
            player.setItemOnCursor(ItemStack.NULL_ITEMSTACK);
            response(session, message, true);
            return;
        }
        
        int slot = inv.getSlotConverter().netToLocal(message.getSlot());

        if (slot < 0) {
            response(session, message, false);
            player.getServer().getLogger().log(Level.WARNING, "Got invalid inventory slot " + message.getSlot() + " from " + player.getName());
            return;
        }
        
        ItemStack currentItem = inv.getItem(slot);

        if (player.getGamemode() == GameMode.CREATIVE && message.getWindowId() == inv.getId()) {
            response(session, message, false);
            player.onSlotSet(inv, slot, currentItem);
            player.getServer().getLogger().log(Level.WARNING, player.getName() + " tried an invalid inventory action in Creative mode!");
            return;
        }
        if (currentItem == null || currentItem == ItemStack.NULL_ITEMSTACK) {
            if (message.getClickedItem() != null && message.getClickedItem() != ItemStack.NULL_ITEMSTACK && message.getClickedItem().getId() != -1) {
                player.onSlotSet(inv, slot, currentItem);
                response(session, message, false);
                return;
            }
        } else if (!message.getClickedItem().doItemsMatch(currentItem)) {
            player.onSlotSet(inv, slot, currentItem);
            response(session, message, false);
            return;
        }
        
        if (message.getMode() == 1) {
            /*if (inv == player.getInventory().getOpenWindow()) {
                // TODO: if player has e.g. chest open
            } else if (inv == player.getInventory().getCraftingInventory()) {
               // TODO: crafting stuff
            } else {*/
                if (slot < 9) {
                    for (int i = 9; i < 36; ++i) {
                        if (inv.getItem(i) == null || inv.getItem(i) == ItemStack.NULL_ITEMSTACK) {
                            // FIXME itemstacks
                            inv.setItem(i, currentItem);
                            inv.setItem(slot, ItemStack.NULL_ITEMSTACK);
                            response(session, message, true);
                            return;
                        }
                    }
                } else {
                    for (int i = 0; i < 9; ++i) {
                        if (inv.getItem(i) == null || inv.getItem(i) == ItemStack.NULL_ITEMSTACK) {
                            // FIXME itemstacks
                            inv.setItem(i, currentItem);
                            inv.setItem(slot, ItemStack.NULL_ITEMSTACK);
                            response(session, message, true);
                            return;
                        }
                    }
                }
            //}
            response(session, message, false);
            return;
        }
        
        /*if (inv == player.getInventory().getCraftingInventory() && slot == CraftingInventory.RESULT_SLOT && player.getItemOnCursor() != null) {
            response(session, message, false);
            return;
        }*/
        
        response(session, message, true);
        inv.setItem(slot, player.getItemOnCursor());
        player.setItemOnCursor(currentItem);
        
        /*if (inv == player.getInventory().getCraftingInventory() && slot == CraftingInventory.RESULT_SLOT && currentItem != null) {
            player.getInventory().getCraftingInventory().craft();
        }*/
    }
    
    private void response(Session session, ClickWindowPacket message, boolean success) {
        session.send(new ConfirmTransactionPacket(message.getWindowId(), message.getActionId(), success));
    }

}

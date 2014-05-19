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

    @SuppressWarnings("unused")
	@Override
    public void handle(Session session, Player player, ClickWindowPacket message) {
        if (player == null)
            return;
        
        PlayerInventory inv = player.getInventory();
        int slot = message.getSlot();
        
        // Modify slot if needed
        if (slot < 0) {
            // TODO inv = player.getInventory().getCraftingInventory();
            slot = message.getSlot();
        }
        if (slot == -1) {
            player.setItemOnCursor(null);
            response(session, message, true);
        }
        if (slot < 0) {
            response(session, message, false);
            player.getServer().getLogger().log(Level.WARNING, "Got invalid inventory slot {0} from {1}", new Object[]{message.getSlot(), player.getName()});
            return;
        }
        
        ItemStack currentItem = inv.getItem(slot);

        if (player.getGamemode() == GameMode.CREATIVE && message.getWindowId() == inv.getId()) {
            response(session, message, false);
            player.onSlotSet(inv, slot, currentItem);
            player.getServer().getLogger().log(Level.WARNING, "{0} tried to do an invalid inventory action in Creative mode!", new Object[]{player.getName()});
            return;
        }
        if (currentItem == null) {
            if (message.getClickedItem().getId() != -1) {
                player.onSlotSet(inv, slot, currentItem);
                response(session, message, false);
                return;
            }
        } else if (message.getClickedItem().getId() != currentItem.getId() ||
                message.getClickedItem().getCount() != currentItem.getCount() ||
                message.getClickedItem().getDamage() != currentItem.getDamage()) {
            player.onSlotSet(inv, slot, currentItem);
            response(session, message, false);
            return;
        }
        
        if (message.getMode() == 1) {
            if (false /* inv == player.getInventory().getOpenWindow() */) {
                // TODO: if player has e.g. chest open
            //} else if (inv == player.getInventory().getCraftingInventory()) {
            //    // TODO: crafting stuff
            } else {
                if (slot < 9) {
                    for (int i = 9; i < 36; ++i) {
                        if (inv.getItem(i) == null) {
                            // TODO: deal with item stacks
                            inv.setItem(i, currentItem);
                            inv.setItem(slot, null);
                            response(session, message, true);
                            return;
                        }
                    }
                } else {
                    for (int i = 0; i < 9; ++i) {
                        if (inv.getItem(i) == null) {
                            // TODO: deal with item stacks
                            inv.setItem(i, currentItem);
                            inv.setItem(slot, null);
                            response(session, message, true);
                            return;
                        }
                    }
                }
            }
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

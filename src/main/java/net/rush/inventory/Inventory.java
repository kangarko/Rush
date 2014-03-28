package net.rush.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import net.rush.model.Item;
import net.rush.model.Player;

import org.bukkit.Material;

@SuppressWarnings("deprecation")
public abstract class Inventory {

    private final int id;

    protected final ArrayList<Player> viewers = new ArrayList<Player>();

    private final Item[] slots;

    protected Inventory(int id, int size) {
        this.id = id;
        slots = new Item[size];
    }

    public void addViewer(Player viewer) {
        viewers.add(viewer);
    }

    public void removeViewer(Player viewer) {
        viewers.remove(viewer);
    }

    public abstract SlotConverter getSlotConverter();

    public int getId() {
        return id;
    }

    public int getSize() {
        return slots.length;
    }

    public abstract String getName();

    private void sendUpdate(int index) {
        for (Player viewer : viewers) {
        	System.out.println(viewer.getName() + " recieved update!");
            viewer.onSlotSet(this, index, slots[index]);
        }
    }

    public Item getItem(int index) {
        return slots[index];
    }

    public void setItem(int index, Item item) {
        slots[index] = item;
        sendUpdate(index);
    }

    public HashMap<Integer, Item> addItem(Item... items) {
        HashMap<Integer, Item> result = new HashMap<Integer, Item>();

        for (int i = 0; i < items.length; ++i) {
            int maxStackSize = Material.getMaterial(items[i].getId())
                    .getMaxStackSize();
			int mat = items[i].getId();
            int toAdd = items[i].getCount();
            short damage = (short) items[i].getDamage();

            for (int j = 0; toAdd > 0 && j < getSize(); ++j) {
                if (slots[j] != null && slots[j].getId() == mat
                        && slots[j].getDamage() == damage) {
                    int space = maxStackSize - slots[j].getCount();
                    if (space < 0)
                        continue;
                    if (space > toAdd)
                        space = toAdd;

                    slots[j].setCount(slots[j].getCount() + space);
                    toAdd -= space;
                    sendUpdate(j);
                }
            }

            if (toAdd > 0) {
                for (int j = 0; toAdd > 0 && j < getSize(); ++j) {
                    if (slots[j] == null) {
                        int num = toAdd > maxStackSize ? maxStackSize : toAdd;
                        slots[j] = new Item(mat, num, damage);
                        toAdd -= num;
                        sendUpdate(j);
                    }
                }
            }

            if (toAdd > 0) {
                result.put(i, new Item(mat, toAdd, damage));
            }
        }

        return result;
    }

    public HashMap<Integer, Item> removeItem(Item... items) {
        HashMap<Integer, Item> result = new HashMap<Integer, Item>();

        for (int i = 0; i < items.length; ++i) {
            int mat = items[i].getId();
            int toRemove = items[i].getCount();
            short damage = (short) items[i].getDamage();

            for (int j = 0; j < getSize(); ++j) {
                if (slots[j] != null && slots[j].getId() == mat
                        && slots[j].getDamage() == damage) {
                    if (slots[j].getCount() > toRemove) {
                        slots[j].setCount(slots[j].getCount() - toRemove);
                    }
                    else {
                        toRemove -= slots[j].getCount();
                        slots[j] = null;
                    }
                    sendUpdate(j);
                }
            }

            if (toRemove > 0) {
                result.put(i, new Item(mat, toRemove, damage));
            }
        }

        return result;
    }

    public Item[] getContents() {
        return slots.clone();
    }

    public void setContents(Item[] items) {
        if (items.length != slots.length) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < items.length; ++i) {
            setItem(i, items[i]);
        }
    }

    public boolean contains(int materialId) {
        return first(materialId) >= 0;
    }

    public boolean contains(Material material) {
        return first(material) >= 0;
    }

    public boolean contains(Item item) {
        return first(item) >= 0;
    }

    public boolean contains(Material material, int amount) {
        return contains(material.getId(), amount);
    }

    public boolean contains(Item item, int amount) {
        return contains(item.getId(), amount);
    }

    public boolean contains(int materialId, int amount) {
        HashMap<Integer, ? extends Item> found = all(materialId);
        int total = 0;
        for (Item stack : found.values()) {
            total += stack.getCount();
        }
        return total >= amount;
    }

    public HashMap<Integer, Item> all(Material material) {
        return all(material.getId());
    }

    public HashMap<Integer, Item> all(int materialId) {
        HashMap<Integer, Item> result = new HashMap<Integer, Item>();
        for (int i = 0; i < slots.length; ++i) {
            if (slots[i].getId() == materialId) {
                result.put(i, slots[i]);
            }
        }
        return result;
    }

    public HashMap<Integer, Item> all(Item item) {
        HashMap<Integer, Item> result = new HashMap<Integer, Item>();
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null && slots[i].equals(item)) {
                result.put(i, slots[i]);
            }
        }
        return result;
    }

    public int first(Material material) {
        return first(material.getId());
    }

    public int first(int materialId) {
        for (int i = 0; i < slots.length; ++i) {
            if (slots[i] != null && slots[i].getId() == materialId)
                return i;
        }
        return -1;
    }

    public int first(Item item) {
        for (int i = 0; i < slots.length; ++i) {
            if (slots[i] != null && slots[i].equals(item))
                return i;
        }
        return -1;
    }

    public int firstEmpty() {
        for (int i = 0; i < slots.length; ++i) {
            if (slots[i] == null)
                return i;
        }
        return -1;
    }

    public void remove(int materialId) {
        HashMap<Integer, ? extends Item> stacks = all(materialId);
        for (Integer slot : stacks.keySet()) {
            setItem(slot, null);
        }
    }

    public void remove(Material material) {
        HashMap<Integer, ? extends Item> stacks = all(material);
        for (Integer slot : stacks.keySet()) {
            setItem(slot, null);
        }
    }

    public void remove(Item item) {
        HashMap<Integer, ? extends Item> stacks = all(item);
        for (Integer slot : stacks.keySet()) {
            setItem(slot, null);
        }
    }

    public void clear(int index) {
        setItem(index, null);
    }

    public void clear() {
        for (int i = 0; i < slots.length; ++i) {
            clear(i);
        }
    }

}

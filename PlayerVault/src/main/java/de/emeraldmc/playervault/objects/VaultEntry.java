package de.emeraldmc.playervault.objects;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class VaultEntry {
    private int slot;
    private ItemStack stack;

    public VaultEntry(int slot, ItemStack stack) {
        this.slot = slot;
        this.stack = stack;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getStack() {
        return stack;
    }
}

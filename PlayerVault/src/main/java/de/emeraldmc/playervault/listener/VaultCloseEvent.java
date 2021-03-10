package de.emeraldmc.playervault.listener;

import de.emeraldmc.playervault.PlayerVault;
import de.emeraldmc.playervault.guis.AbstractGUI;
import de.emeraldmc.playervault.guis.VaultGui;
import de.emeraldmc.playervault.objects.VaultEntry;
import de.emeraldmc.playervault.objects.VaultStorage;
import de.emeraldmc.playervault.util.Serializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.UUID;

public class VaultCloseEvent implements Listener {

    @EventHandler
    public void invCloseEvent(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        UUID playerUUID = player.getUniqueId();

        UUID inventoryUUID = AbstractGUI.openInventories.get(playerUUID);
        if (inventoryUUID == null) return;


        ArrayList<VaultEntry> entries = new ArrayList<>();
        for (int i = 0; i < 6*9; i++) {
            if (e.getInventory().getItem(i) != null) {
                entries.add(new VaultEntry(i, e.getInventory().getItem(i)));
            }
        }


        AbstractGUI gui = AbstractGUI.getInventoriesByUUID().get(inventoryUUID);
        if (gui instanceof VaultGui) {
            VaultGui vaultGui = (VaultGui) gui;
            vaultGui.updateVaultEntries(entries);
            PlayerVault.vaultDB.updateVaultStorage(vaultGui.getStorage());
        }
        AbstractGUI.openInventories.remove(playerUUID);
    }
}

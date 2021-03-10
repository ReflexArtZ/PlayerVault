package de.emeraldmc.playervault.objects;

import de.emeraldmc.playervault.PlayerVault;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.UUID;

public class VaultStorage {
    private UUID playerUUID;

    private int vaultID;
    private ArrayList<VaultEntry> vaultEntries;


    public VaultStorage(UUID playerUUID, int vaultID, ArrayList<VaultEntry> vaultEntries) {
        this.playerUUID = playerUUID;
        this.vaultID = vaultID;
        this.vaultEntries = vaultEntries;
    }

    public void openChest() {
        Inventory inventory = Bukkit.createInventory(null, 6*9, PlayerVault.configuration.getGuiTitle());
        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null || !player.isOnline()) return;

        player.openInventory(inventory);
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public int getVaultID() {
        return vaultID;
    }

    public ArrayList<VaultEntry> getVaultEntries() {
        return vaultEntries;
    }

    public VaultStorage setVaultEntries(ArrayList<VaultEntry> vaultEntries) {
        this.vaultEntries = vaultEntries;
        return this;
    }
}

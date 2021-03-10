package de.emeraldmc.playervault.io.sqlite;

import de.emeraldmc.playervault.objects.VaultEntry;
import de.emeraldmc.playervault.objects.VaultStorage;
import de.emeraldmc.playervault.util.Debug;
import de.emeraldmc.playervault.util.Serializer;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerVaultDB {
    private DBController controller;

    public PlayerVaultDB(DBController controller) {
        this.controller = controller;
        if (controller == null) return;
        createVaultTable();
    }

    synchronized private void createVaultTable() {
        String sql = "CREATE TABLE IF NOT EXISTS playervault (ID INTEGER PRIMARY KEY AUTO_INCREMENT,uuid VARCHAR(36) NOT NULL,vaultID INTEGER NOT NULL,slot INTEGER NOT NULL,item TEXT NOT NULL);";
        try (Statement stmnt = controller.getStatement()) {
            stmnt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    synchronized public void updateVaultStorage(VaultStorage storage) {
        if (storage == null) return;
        if (existVaultStorage(storage.getPlayerUUID(), storage.getVaultID())) {
            deleteVaultStorage(storage.getPlayerUUID(), storage.getVaultID());
        }
        addVaultStorage(storage);

    }
    synchronized public boolean existVaultStorage(UUID playerUUID, int vaultID) {
        boolean exist = false;
        try (PreparedStatement stmnt = controller.getPreparedStatement("SELECT COUNT(*) AS cnt FROM playervault WHERE uuid=? AND vaultID=?")){
            stmnt.setString(1, playerUUID.toString());
            stmnt.setInt(2, vaultID);

            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                int count = rs.getInt("cnt");
                exist = count > 0;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }
    synchronized public void deleteVaultStorage(UUID playerUUID, int vaultID) {
        try (PreparedStatement stmnt = controller.getPreparedStatement("DELETE FROM playervault WHERE uuid=? AND vaultID=?")) {
            stmnt.setString(1, playerUUID.toString());
            stmnt.setInt(2, vaultID);
            stmnt.executeUpdate();
            Debug.print("Removed Vault Storage > "+"UUID: "+playerUUID.toString()+"\n"+"VaultID: "+vaultID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    synchronized public void addVaultStorage(VaultStorage storage) {
        if (storage == null) return;
        for (VaultEntry entry : storage.getVaultEntries()) {
            try (PreparedStatement stmnt = controller.getPreparedStatement("INSERT INTO playervault (uuid, vaultID, slot, item) VALUES (?, ?, ?, ?)")) {
                stmnt.setString(1, storage.getPlayerUUID().toString());
                stmnt.setInt(2, storage.getVaultID());
                stmnt.setInt(3, entry.getSlot());
                stmnt.setString(4, Serializer.itemStackArrayToBase64(new ItemStack[]{entry.getStack()}));
                stmnt.executeUpdate();

                Debug.print("Added Vault Storage!");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized public VaultStorage getVaultStorage(UUID playerUUID, int vaultID) {
        if (playerUUID == null) return null;
        VaultStorage storage = null;
        ArrayList<VaultEntry> entries = new ArrayList<>();

        try (PreparedStatement stmnt = controller.getPreparedStatement("SELECT * FROM playervault WHERE uuid=? AND vaultID=?")){
            stmnt.setString(1, playerUUID.toString());
            stmnt.setInt(2, vaultID);

            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                int slot = rs.getInt("slot");
                ItemStack[] stack = Serializer.itemStackArrayFromBase64(rs.getString("item"));
                entries.add(new VaultEntry(slot, stack[0]));
            }
            rs.close();
            storage = new VaultStorage(playerUUID, vaultID, entries);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return storage;
    }
}

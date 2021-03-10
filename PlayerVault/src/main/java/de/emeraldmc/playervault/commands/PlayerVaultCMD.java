package de.emeraldmc.playervault.commands;

import de.emeraldmc.playervault.PlayerVault;
import de.emeraldmc.playervault.guis.VaultGui;
import de.emeraldmc.playervault.objects.VaultEntry;
import de.emeraldmc.playervault.objects.VaultStorage;
import de.emeraldmc.playervault.util.ChatAPI;
import de.emeraldmc.playervault.util.Serializer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.ArrayList;

public class PlayerVaultCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        if (args.length != 1) return false;

        int vaultID = 0;


        try {
            vaultID = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
            return false;
        }

        Player player = (Player) sender;

        // todo check perm
        boolean havePerms = false;
        for (PermissionAttachmentInfo permissionAttachmentInfo : player.getEffectivePermissions()) {
            if (permissionAttachmentInfo.getPermission().startsWith("playervault.vaults")) {
                havePerms = true;
                String s = permissionAttachmentInfo.getPermission();
                System.out.println(s);
                String intValue = s.replaceAll("[^0-9]", "");
                System.out.println(intValue);

                int maxCount = Integer.parseInt(intValue);
                if (vaultID > maxCount) {
                    ChatAPI.sendMessage(player, PlayerVault.configuration.getMaxCount().replaceAll("%max%", String.valueOf(maxCount)));
                    return true;
                }
            }
        }
        if (!havePerms) {
            ChatAPI.sendMessage(player, PlayerVault.configuration.getNoPermission());
            return true;
        }
        player.getEffectivePermissions().forEach(p -> p.getPermission().startsWith("playervault.vaults"));

        VaultStorage storage = PlayerVault.vaultDB.getVaultStorage(player.getUniqueId(), vaultID);

        VaultGui vaultGui = new VaultGui(storage);
        vaultGui.open(player);

        return false;
    }
}

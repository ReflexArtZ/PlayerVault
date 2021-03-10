package de.emeraldmc.playervault.commands;

import de.emeraldmc.playervault.PlayerVault;
import de.emeraldmc.playervault.util.ChatAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PlayerVault.getInstance().reload();
        ChatAPI.sendMessage(sender, "§6 Reloaded Plugin!");

        return true;
    }
}

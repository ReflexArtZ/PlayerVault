package de.emeraldmc.playervault.util;

import de.emeraldmc.playervault.PlayerVault;
import org.bukkit.Bukkit;

public class Debug {
    public static void print(String s) {
        if (PlayerVault.configuration.isDebug()) {
            Bukkit.getConsoleSender().sendMessage(ChatAPI.translateColor(PlayerVault.configuration.getPrefix())+"§c§lDEBUG §r> "+s);
        }
    }
}

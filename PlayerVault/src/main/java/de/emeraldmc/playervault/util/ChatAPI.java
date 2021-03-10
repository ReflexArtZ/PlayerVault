package de.emeraldmc.playervault.util;

import de.emeraldmc.playervault.PlayerVault;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatAPI {

    /**
     * Translates Colorcode
     * @param s String with old Colorcode
     * @return Translated String
     */
    public static String translateColor(String s) {
        s = ChatColor.translateAlternateColorCodes('&', s);
        return s;
    }


    /**
     * Send Message with prefix to a Player
     * @param p Player
     * @param msg Message
     */
    public static void sendMessage(Player p, String msg) {
        p.sendMessage(PlayerVault.configuration.getPrefix()+translateColor(msg));
    }

    /**
     * Send Message with prefix to Console
     * @param sender ConsoleSender
     * @param msg Message
     */
    public static void sendMessage(CommandSender sender, String msg) {
        sender.sendMessage(PlayerVault.configuration.getPrefix()+translateColor(msg));
    }
}

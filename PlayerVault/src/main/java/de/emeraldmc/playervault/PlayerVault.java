package de.emeraldmc.playervault;

import de.emeraldmc.playervault.commands.PlayerVaultCMD;
import de.emeraldmc.playervault.commands.ReloadCMD;
import de.emeraldmc.playervault.io.StandardConfiguration;
import de.emeraldmc.playervault.io.sqlite.DBController;
import de.emeraldmc.playervault.io.sqlite.PlayerVaultDB;
import de.emeraldmc.playervault.listener.VaultCloseEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

public final class PlayerVault extends JavaPlugin {

    private static PlayerVault instance;

    public static StandardConfiguration configuration;

    public static PlayerVaultDB vaultDB;

    private DBController controller = null;

    public static PlayerVault getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        initializeStandartConfig();
        configuration = new StandardConfiguration();

        try {
            controller = new DBController(configuration.getMysqlUser(), configuration.getMysqlPass(), configuration.getMysqlHost(), configuration.getMysqlPort(), configuration.getMysqlDatabase());
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(configuration.getPrefix()+"§c[-] Error while connecting to database! Maybe you have not configured it?");
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(configuration.getPrefix()+"§cShutting Down in 20!");
            Bukkit.getScheduler().runTaskLater(this, () -> Bukkit.getServer().shutdown(), 20);
        } catch (ClassNotFoundException e) {
            Bukkit.getConsoleSender().sendMessage(configuration.getPrefix()+"§c[-] Error while connecting to database! Maybe you have not configured it?");
            Bukkit.getConsoleSender().sendMessage(configuration.getPrefix()+"§cShutting Down in 20!");
            e.printStackTrace();
            Bukkit.getScheduler().runTaskLater(this, () -> Bukkit.getServer().shutdown(), 20);
        }
        vaultDB = new PlayerVaultDB(controller);

        initCommands();
        initListeners();
    }

    @Override
    public void onDisable() {
        if (controller == null) return;
            try {
                if (controller.getConnection() != null && !controller.getConnection().isClosed()) {
                    controller.closeConnection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void reload() {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            if (controller != null) {
                try {
                    if (controller.getConnection() != null && !controller.getConnection().isClosed()) {
                        controller.getConnection().close();
                    }
                    controller = new DBController(configuration.getMysqlUser(), configuration.getMysqlPass(), configuration.getMysqlHost(), configuration.getMysqlPort(), configuration.getMysqlDatabase());
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        initializeStandartConfig();
        configuration = new StandardConfiguration();
    }

    /**
     * Loading standart config
     */
    private void initializeStandartConfig() {
        File f = new File(getDataFolder()+ File.separator+ "config.yml");
        if(!f.exists())
        {
            this.saveDefaultConfig();
        }
        this.reloadConfig();
    }

    private void initCommands() {
        getCommand("avault").setExecutor(new PlayerVaultCMD());
        getCommand("avreload").setExecutor(new ReloadCMD());
    }
    private void initListeners() {
        Bukkit.getPluginManager().registerEvents(new VaultCloseEvent(), this);
    }
}

package de.emeraldmc.playervault.io;


import de.emeraldmc.playervault.PlayerVault;
import de.emeraldmc.playervault.util.ChatAPI;

public class StandardConfiguration {
    private final boolean debug;
    private final String prefix;
    private final String guiTitle;
    private final String maxCount;
    private final String noPermission;

    private final String mysqlUser;
    private final String mysqlPass;
    private final String mysqlHost;
    private final int mysqlPort;
    private final String mysqlDatabase;


    public StandardConfiguration() {
        prefix = getMessage("Config.prefix");
        debug = getBoolean("Config.debug");
        guiTitle = getMessage("Config.guiTitle");
        maxCount = getMessage("Config.maxCount");
        noPermission = getMessage("Config.noPermission");

        mysqlUser = getString("Config.MySQL.user");
        mysqlPass = getString("Config.MySQL.password");
        mysqlHost = getString("Config.MySQL.host");
        mysqlPort = getInt("Config.MySQL.port");
        mysqlDatabase = getString("Config.MySQL.database");
    }

    private String getMessage(String path) {
        return ChatAPI.translateColor(getString(path));
    }

    private int getInt(String path) {
        return PlayerVault.getInstance().getConfig().getInt(path);
    }

    private double getDouble(String path) {
        return PlayerVault.getInstance().getConfig().getDouble(path);
    }

    private String getString(String path) {
        return PlayerVault.getInstance().getConfig().getString(path);
    }

    private boolean getBoolean(String path) {
        return PlayerVault.getInstance().getConfig().getBoolean(path);
    }


    /**
     * Updates certain config values, values will not be saved if saveConfig() is not executed afterwards
     * To refresh the StandartConfiguration values see refresh()
     *
     * @param path
     * @param o
     */
    public void update(String path, Object o) {
        PlayerVault.getInstance().getConfig().set(path, o);
    }

    public void saveConfig() {
        PlayerVault.getInstance().saveConfig();
    }

    /**
     * Refreshes the StandartConfiguration values
     */
    public void refresh() {
        PlayerVault.getInstance().reloadConfig();
        PlayerVault.configuration = new StandardConfiguration();
    }

    public boolean isDebug() {
        return debug;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getMysqlUser() {
        return mysqlUser;
    }

    public String getMysqlPass() {
        return mysqlPass;
    }

    public String getMysqlHost() {
        return mysqlHost;
    }

    public int getMysqlPort() {
        return mysqlPort;
    }

    public String getMysqlDatabase() {
        return mysqlDatabase;
    }

    public String getGuiTitle() {
        return guiTitle;
    }

    public String getMaxCount() {
        return maxCount;
    }

    public String getNoPermission() {
        return noPermission;
    }

    // --- getter  ---

}


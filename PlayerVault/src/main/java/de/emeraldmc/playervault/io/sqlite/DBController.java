package de.emeraldmc.playervault.io.sqlite;

import de.emeraldmc.playervault.util.Debug;

import java.sql.*;
import java.util.Objects;

/**
 * Controller for controlling database access
 */
public class DBController {

    private String user;
    private String pass;
    private String host;
    private int port;
    private String database;

    private Connection connection;


    public DBController(String user, String pass, String host, int port, String database) throws SQLException, ClassNotFoundException {
        this.user = user;
        this.pass = pass;
        this.host = host;
        this.port = port;
        this.database = database;
        openConnection();
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
        }
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database+"?autoReconnect=true", this.user, this.pass);

        Debug.print("Connected to SQLite database!");
    }
    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
            Debug.print("Closed SQLite connection!");
        }
    }
    public Statement getStatement() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                openConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection.createStatement();
    }
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                openConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection.prepareStatement(sql);
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public Connection getConnection() {
        return connection;
    }
}

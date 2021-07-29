package de.towerwars.mysql;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MySqlConnection {

    private final String ip, port, database, username, password;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private Connection connection;

    public MySqlConnection(final String ip, final String port, final String database, final String username, final String password) {
        this.ip = ip;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void connect() {
        executorService.execute(() -> {
            if (isConnected())
                return;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + database + "?autoReconnect=true", username, password);
                System.out.println("It was successfully connected to the database");
                update("CREATE TABLE IF NOT EXISTS towerplayer(UUID char(36) UNIQUE, NAME varchar(16), WINS int, SKILL int, PLAYED int);");
                update("CREATE TABLE IF NOT EXISTS locations(NAME varchar(16) UNIQUE, WORLD varchar(20), POSX double, POSY double, POSZ double, YAW float, PITCH float);");
            } catch (final SQLException exception) {
                exception.printStackTrace();
                System.out.println("It could not connected to the database");
                System.out.println("jdbc:mysql://" + ip + ":" + port + "/" + database + "?autoReconnect=true" + username + password);
            }
        });
    }

    public void disconnect() {
        executorService.execute(() -> {
            if (!isConnected()) return;
            try {
                connection.close();
                System.out.println("The connection to the database was successfully closed");
            } catch (final SQLException exception) {
                System.out.println("The connection to the database could not be closed");
            }
        });
    }

    public PreparedStatement getStatement(final String sql) throws SQLException {
        return !isConnected() ? null : connection.prepareStatement(sql);
    }

    private void update(String query) {
        this.executorService.execute(() -> {
            try {
                final Statement statement = this.connection.createStatement();
                statement.execute(query);
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}

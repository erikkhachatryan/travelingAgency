package service.dao;

import service.util.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Levon on 07-Dec-17.
 */
public class DataSource {

    private String dbName;
    private String serverName;
    private String jdbcPort;
    private String userName;
    private String password;

    public DataSource(String dbName, String serverName, String jdbcPort, String userName, String password) {
        this.dbName = dbName;
        this.serverName = serverName;
        this.jdbcPort = jdbcPort;
        this.userName = userName;
        this.password = password;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://" + serverName + ":" + jdbcPort + ";databaseName=" + dbName;
            connection = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

}

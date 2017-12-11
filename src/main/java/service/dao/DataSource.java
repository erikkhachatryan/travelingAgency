package service.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Erik on 07-Dec-17.
 */
public class DataSource {

    private String dbName;
    private String serverName;
    private String jdbcPort;
    private String userName;
    private String password;
    private Properties applicationProperties;

//    public DataSource(String dbName, String serverName, String jdbcPort, String userName, String password) {
//        this.dbName = dbName;
//        this.serverName = serverName;
//        this.jdbcPort = jdbcPort;
//        this.userName = userName;
//        this.password = password;
//    }

    public DataSource() {
        this.dbName = getApplicationProperties().getProperty("db.name");
        this.serverName = getApplicationProperties().getProperty("server.name");
        this.jdbcPort = getApplicationProperties().getProperty("jdbc.port");
        this.userName = getApplicationProperties().getProperty("user.name");
        this.password = getApplicationProperties().getProperty("user.password");
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://" + serverName + ":" + jdbcPort + ";databaseName=" + dbName;
            connection = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Properties getApplicationProperties() {
        if (applicationProperties == null) {
            applicationProperties = new Properties();
            try {
                applicationProperties.load(new FileInputStream("C:\\Projects\\DeCore\\Traveling Agency\\travelingAgency\\src\\main\\resources\\properties\\application.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return applicationProperties;
    }
}

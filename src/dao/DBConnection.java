package System.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    private DBConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://webnoithat-webnoithat.j.aivencloud.com:11125/qlksjava?useSSL=true&verifyServerCertificate=false";
            String user = "avnadmin";
            String password = "AVNS_P6P1JvIl-HrMi2D5hgm";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to Aiven MySQL!");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL driver not found", e);
        }
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
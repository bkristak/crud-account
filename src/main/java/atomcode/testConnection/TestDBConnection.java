package atomcode.testConnection;

import atomcode.db.HikariCPDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDBConnection {
    public static void main(String[] args) {
        System.out.println("Starting connection test.");
        try (Connection connection = HikariCPDataSource.getConnection()) {
            if (connection != null) {
                System.out.println("Connection to MySQL successful.");
            } else {
                System.out.println("Failed to make connection to MySQL.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL exception: " + e.getMessage());
        }
    }
}

package atomcode.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariCPDataSource {
    public static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource datasource;
    private static final Logger logger = LoggerFactory.getLogger(HikariCPDataSource.class);

    private HikariCPDataSource() {}

    static {
        final Properties properties = new Properties();
        try {
            properties.load(HikariCPDataSource.class.getResourceAsStream("/application.properties"));
        } catch (Exception e) {
            logger.error("Error while loading application properties", e);
        }

        config.setJdbcUrl("jdbc:mysql://localhost:3306/" + properties.getProperty("db.name"));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));

        datasource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

}

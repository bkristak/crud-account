package atomcode.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

public class TransactionDAO {
    private static final String INSERT_TRANSACTION = "INSERT INTO transaction (account_id, amount) VALUES (?, ?)";
    private static final String READ_ACCOUNT_BALANCE = "SELECT SUM(amount) AS account_balance FROM transaction WHERE account_id = ?";

    private static final Logger logger = getLogger(AccountDAO.class);

    public int createTransaction(int accountId, double transactionAmount) {
        try (
                Connection connection = HikariCPDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_TRANSACTION)
                ) {
            statement.setInt(1, accountId);
            statement.setDouble(2, transactionAmount);
            return statement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error while creating DB record.", e);
            return 0;
        }
    }

    public double getAccountBalance(int accountId) throws SQLException {
        double accountBalance = 0;
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ACCOUNT_BALANCE)) {
            statement.setInt(1, accountId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    accountBalance = resultSet.getDouble("account_balance");
                }
            }
        } catch (SQLException e) {
            logger.error("Error while reading accounts: ", e);
            throw e;
        }
        return accountBalance;
    }
}

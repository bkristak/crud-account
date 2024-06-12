package atomcode.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

public class AccountDAO {

    private static final String CREATE_ACCOUNT = "INSERT INTO account (account_name) VALUES (?)";
    private static final String READ_ACCOUNTS = "SELECT * FROM account";
    private static final Logger logger = getLogger(AccountDAO.class);

    public int createAccount(String accountName) {
        try (
                Connection conn = HikariCPDataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(CREATE_ACCOUNT);
        ) {
            statement.setString(1, accountName);
            return statement.executeUpdate();


        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Account with this name already exists.");
            return 0;
        } catch (SQLException e) {
            logger.error("Error while creating new account", e);
            return 0;
        }
    }

    public List<Account> readAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ACCOUNTS);
             ResultSet resultSet = statement.executeQuery()) {
            if(!resultSet.isBeforeFirst()) {
                System.out.println("DB is currently empty, no user account available.");
                return accounts;
            } else {
                while (resultSet.next()) {
                    int accountId = resultSet.getInt("id");
                    String accountName = resultSet.getString("account_name");
                    Account account = new Account();
                    account.setAccountName(accountName);
                    account.setId(accountId);
                    accounts.add(account);
                }
            }

        } catch (SQLException e) {
            logger.error("Error while reading accounts: " + e);
            return null;
        }
        return accounts;
    }
}

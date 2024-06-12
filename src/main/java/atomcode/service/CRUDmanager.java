package atomcode.service;

import atomcode.db.Account;
import atomcode.db.AccountDAO;
import atomcode.db.TransactionDAO;
import atomcode.utility.InputUtils;

import java.sql.SQLException;
import java.util.List;

public class CRUDmanager {
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    public CRUDmanager() {
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    public void printOptions() throws SQLException {
        System.out.println("Program started.\n");
        while(true) {
            System.out.println("\nSelect an option:");
            System.out.println("1 - create account with starting balance");
            System.out.println("2 - insert or withdraw money");
            System.out.println("3 - show account balance");
            System.out.println("4 - Exit the program");
            System.out.print("User input: ");
            final int userChoice = InputUtils.readInt();
            switch (userChoice) {
                case 1 -> {
                    System.out.println("User selected to create new account.\n");
                    createNewAccount();
                }
                case 2 -> {
                    System.out.println("User selected to insert or withdraw money.\n");
                    List<Account> listAccounts = printAvailableUserAccounts();
                    if (listAccounts == null || listAccounts.isEmpty()) {
                        System.out.println("No accounts available to perform transactions.");
                    } else {
                        int accountId = selectAccount(listAccounts);
                        insertOrWithdrawMoney(accountId);
                    }
                }
                case 3 -> {
                    System.out.println("User selected to show account balance.\n");
                    List<Account> listAccounts = printAvailableUserAccounts();
                    if (listAccounts == null || listAccounts.isEmpty()) {
                        System.out.println("No accounts available to show balance.");
                    } else {
                        int accountId = selectAccount(listAccounts);
                        printAccountBalance(accountId);
                    }
                }
                case 4 -> {
                    System.out.println("Good bye!");
                    return;
                }
                default -> System.out.println("Invalid user choice.");
            }
        }
    }

    public List<Account> printAvailableUserAccounts(){
        System.out.println("Available user accounts: ");
        List<Account> listAccounts = accountDAO.readAccounts();
        for (int i = 0; i < listAccounts.size(); i++) {
            System.out.println(i+1 + " - " + listAccounts.get(i).getAccountName());
        }
        return listAccounts;
    }

    public int selectAccount(List<Account> listAccounts){
        int accountId = 0;
        while (true) {
            System.out.println("\nSelect user account: ");
            int selectedAccount = InputUtils.readInt();
            if (selectedAccount > 0 && selectedAccount <= listAccounts.size()) {
                accountId = listAccounts.get(selectedAccount - 1).getId();
                break;
            } else {
                System.out.println("Invalid input. Please select valid input from the list.");
            }
        }
        return accountId;
    }

    public void printAccountBalance(int accountId) throws SQLException {
        double accountBalance = transactionDAO.getAccountBalance(accountId);
        System.out.println("\nSelected account balance = " + accountBalance);
    }

    public void insertOrWithdrawMoney(int accountId) throws SQLException {
        double transactionAmount = 0;
        while (true) {
            System.out.print("\nEnter money transaction amount: ");
            transactionAmount = Math.abs(InputUtils.readDouble());

            System.out.println("\nEnter user option:");
            System.out.println("1 - new deposit");
            System.out.println("2 - withdrawal");
            System.out.print("User option: ");
            int userChoice = InputUtils.readInt();

                if (userChoice == 2) {
                    transactionAmount = -transactionAmount;
                    double accountBalance = transactionDAO.getAccountBalance(accountId);
                    if (accountBalance + transactionAmount >= 0) {
                        break;
                    } else {
                        System.out.println("Not enough balance to withdrew required amount. Your available balance is " + accountBalance + ".");
                    }
                } else if (userChoice != 1) {
                    System.out.println("Invalid input");
                } else {
                    break;
                }
        }

       if (transactionDAO.createTransaction(accountId, transactionAmount) > 0) {
           System.out.println("Transaction succesfull!");
       } else {
           System.out.println("Transaction failed.");
       }
    }


    public void createNewAccount() {
        int accountId = 0;
        System.out.print("Enter account name: ");
        final String accountName = InputUtils.readString();
        System.out.print("Enter account's starting balance: ");
        double transactionAmount = InputUtils.readDouble();
        transactionAmount = Math.abs(transactionAmount);

        if (accountDAO.createAccount(accountName) > 0) {
            List<Account> listAccounts = accountDAO.readAccounts();
            int createdAccountListIndex = listAccounts.size() - 1;
            for (int i = createdAccountListIndex; i < listAccounts.size(); i++) {
               accountId = listAccounts.get(i).getId();
            }
            if (transactionDAO.createTransaction(accountId, transactionAmount) > 0) {
                System.out.println("Account " + accountName + " created with starting balance " + transactionAmount + ".");
            } else {
                System.out.println("Account created but initial balance input failed.");
            }
        } else {
            System.out.println("Account not created. Try again.");
        }
    }

}

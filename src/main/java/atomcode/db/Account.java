package atomcode.db;

import java.sql.Timestamp;

public class Account {
    private int id;
    private String accountName;
    private double balance;

    public Account() {}

    public Account (int id, String accountName, double balance, Timestamp createdOn) {
        this.id = id;
        this.accountName = accountName;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getAccountName() {
        return accountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

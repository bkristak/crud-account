package atomcode.db;

import java.sql.Time;
import java.sql.Timestamp;

public class Transaction {
    private int id;
    private int accountId;
    private double amount;

    public Transaction() {}

    public Transaction(int id, int accountId, double amount, Timestamp createdOn) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}

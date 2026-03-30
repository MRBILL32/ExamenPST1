package Bank.model;

import java.time.LocalDateTime;

public class Transaction {

    private String fromAccountId;
    private String toAccountId;
    private double amount;
    private LocalDateTime dateTime;

    public Transaction(String fromAccountId, String toAccountId, double amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}

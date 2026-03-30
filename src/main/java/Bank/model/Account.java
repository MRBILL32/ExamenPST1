package Bank.model;

public class Account {

    private String id;
    private double balance;
    private boolean blocked;

    public Account(String id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void debit(double amount) {
        this.balance -= amount;
    }

    public void credit(double amount) {
        this.balance += amount;
    }

    public boolean isBlocked() {
        return blocked;
    }
    public void block() {
        this.blocked = true;
    }
}
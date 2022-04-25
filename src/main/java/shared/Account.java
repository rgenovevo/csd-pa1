package shared;

public class Account {
    private String id;
    private int balance;
    // TODO: extracts

    public Account(String id) {
        this.id = id;
        balance = 0;
    }

    public void addBalance(int value) {
        balance += value;
    }

    public int getBalance() {
        return balance;
    }
}

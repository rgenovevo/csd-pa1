package shared;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private int balance;
    private Set<Transaction> extract;

    public Account(String id) {
        this.id = id;
        balance = 0;
        extract = new LinkedHashSet<>();
    }

    public void addBalance(int value) {
        balance += value;
    }

    public int getBalance() {
        return balance;
    }

    public void addExtract(Transaction t) { extract.add(t); }

    public Set<Transaction> getExtract() { return extract; }
}

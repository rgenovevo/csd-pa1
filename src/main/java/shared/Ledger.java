package shared;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Ledger implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String SYSTEM = "System";

    private int transactionid;
    private Set<Transaction> ledger;
    private Map<String, Account> accounts;

    public Ledger() {
        transactionid = 0;
        ledger = new LinkedHashSet<>();
        accounts = new HashMap<>();
    }

    public boolean addBlock(String origin, String destination, int value) {
        if(checkTransaction(origin, destination, value)) {
            if(!SYSTEM.equals(origin))
                accounts.get(origin).addBalance(-value);
            accounts.get(destination).addBalance(value);
            return true;
        }

        return false;
    }

    public boolean createAccount(String id, int value) {
        if(!accounts.containsKey(id)) {
            accounts.put(id, new Account(id));

            if(addBlock(SYSTEM, id, value))
                return true;
        }

        return false;
    }

    public int getBalance(String id) {
        if(accounts.containsKey(id))
            return accounts.get(id).getBalance();

        return -1;
    }

    private boolean checkTransaction(String origin, String destination, int value) {
        if(SYSTEM.equals(origin))
            return ledger.add(new Transaction(transactionid++, origin, destination, value));
        else
            if(accounts.containsKey(origin) && accounts.containsKey(destination))
                if(accounts.get(origin).getBalance() >= value)
                    return ledger.add(new Transaction(transactionid++, origin, destination, value));

        return false;
    }

    public String getLedger() {
        return ledger.stream().map(Transaction::toString).collect(Collectors.joining("\n"));
    }
    
}

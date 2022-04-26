package shared;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Ledger implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String SYSTEM = "System";

    private int transactionid;
    private int global;
    private Set<Transaction> ledger;
    private Map<String, Account> accounts;

    public Ledger() {
        transactionid = 0;
        global = 0;
        ledger = new LinkedHashSet<>();
        accounts = new HashMap<>();
    }

    public boolean addBlock(String origin, String destination, int value) {
        if( SYSTEM.equals(origin) || ( accounts.containsKey(origin) && accounts.containsKey(destination) && accounts.get(origin).getBalance() >= value ) ) {
            Transaction t = new Transaction(transactionid++, origin, destination, value);

            if (t != null) {
                ledger.add(t);

                if (!SYSTEM.equals(origin)) {
                    accounts.get(origin).addExtract(t);
                    accounts.get(origin).addBalance(-value);
                }
                accounts.get(destination).addExtract(t);
                accounts.get(destination).addBalance(value);

                return true;
            }
        }

        return false;
    }

    public boolean createAccount(String id, int value) {
        if(!accounts.containsKey(id)) {
            accounts.put(id, new Account(id));

            if(addBlock(SYSTEM, id, value)) {
                global += value;
                return true;
            }

            accounts.remove(id);
        }

        return false;
    }

    public int getBalance(String id) {
        if(accounts.containsKey(id))
            return accounts.get(id).getBalance();

        return -1;
    }

    public String getLedger() {
        return ledger.stream()
                .map(Transaction::toString)
                .collect(Collectors.joining("\n"));
    }

    public int getGlobalLedgerValue() {
        return global;
    }

    public int getTotalValue(String[] ids) {
        return accounts.entrySet()
                .stream()
                .filter(map -> Arrays.asList(ids).contains(map.getKey()))
                .mapToInt(map -> map.getValue().getBalance())
                .sum();
    }

    public String getExtract(String id){
            if(accounts.containsKey(id))
                return accounts.get(id)
                        .getExtract()
                        .stream()
                        .map(Transaction::toString)
                        .collect(Collectors.joining("\n"));

            return "NULL";
    }
}

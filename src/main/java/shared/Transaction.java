package shared;

import java.io.Serializable;
import java.util.Objects;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String origin;
    private String destination;
    private int money;

    public Transaction(int id, String origin, String destination, int money){
                this.id = id;
                this.origin = origin;
                this.destination = destination;
                this.money = money;
    }

    public int getId() {
        return id;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() { return destination; }

    public int getMoney() {
        return money;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(" ").append(origin).append(" -> ").append(destination).append(" : ").append(money).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Transaction)) return false;

        return Objects.equals(id, ((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

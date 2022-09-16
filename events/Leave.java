
package events;
import customers.Customer;
import interfaces.Event;
import misc.Pair;
import servers.Server;


public class Leave implements Event {
    private final Customer customer;
    private final double timestamp;
    private static final int PRIO = 0;

    
    Leave(Customer customer, double timestamp) {
        this.customer = customer;
        this.timestamp = timestamp;
    }

    @Override
    public double getTimestamp() {
        return this.timestamp;
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }

    @Override
    public int getPriority() {
        return Leave.PRIO;
    }

    @Override
    public boolean hasNextEvent() {
        return false;
    }

    @Override
    public Pair<Event, Server>execute(Server s) {
        return new Pair<Event, Server>(this, s);
    }

    @Override
    public String toString() {
        return this.timestamp + " " + customer.toString() + " leaves";
    }
}

package events;
import customers.Customer;
import interfaces.Event;
import misc.Pair;
import servers.Server;


public class Leave implements Event {
    private final Customer customer;
    private final Server server;
    private final double timestamp;
    private static final int PRIO = 0;

    
    Leave(Customer customer, Server server, double timestamp) {
        this.customer = customer;
        this.server = server;
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
    public Pair<Event, Server>execute() {
        return new Pair<Event, Server>(this, this.server);
    }

    @Override
    public boolean isArrive() {
        return false;
    }

    @Override
    public boolean isWait() {
        return false;
    }

    @Override
    public boolean isServe() {
        return false;
    }

    @Override
    public boolean isLeave() {
        return true;
    }

    @Override
    public Event updateServer(Server server) {
        return new Leave(this.customer, server, this.timestamp);
    }

    @Override
    public Server getServer () {
        return this.server;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.timestamp) + " " + customer.toString() + " leaves";
    }
}


package events;
import interfaces.Event;
import servers.Server;
import customers.Customer;
import misc.Pair;

public class Served implements Event {
    private final Customer customer;
    private final Server server;
    private final double timestamp;
    private static final int PRIO = 0;


    Served(Customer customer, Server server, double timestamp) {
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
        return Served.PRIO;
    }

    @Override
    public Pair<Event, Server>execute(Server s) {
        return new Pair<Event, Server>(
            new Done(this.customer, s, this.timestamp + this.customer.getServeTime()), 
            s.returnUpdatedServer(this.customer)
            );
    }

    @Override
    public boolean hasNextEvent() {
        return true;
    }

    @Override
    public String toString() {
        return this.timestamp + " " + this.customer.toString() + " served by " + this.server.toString();
    }
}

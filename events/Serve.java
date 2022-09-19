
package events;
import interfaces.Event;
import servers.Server;
import customers.Customer;
import misc.Pair;

public class Serve implements Event {
    private final Customer customer;
    private final Server server;
    private final double timestamp;
    private static final int PRIO = 0;


    Serve(Customer customer, Server server, double timestamp) {
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
        return Serve.PRIO;
    }

    @Override
    public Pair<Event, Server>execute() {
        return new Pair<Event, Server>(
            new Done(this.customer, this.server.returnUpdatedServer(this.customer), this.timestamp + this.customer.getServeTime()), 
            this.server.returnUpdatedServer(this.customer)
            );
    }

    @Override
    public Event updateServer(Server server) {
        return new Serve(this.customer, server, this.timestamp);
    }

    @Override
    public boolean hasNextEvent() {
        return true;
    }

    @Override
    public boolean isArrive() {
        return false;
    }

    @Override
    public String toString() {
        return this.timestamp + " " + this.customer.toString() + " served by " + this.server.toString();
    }
}

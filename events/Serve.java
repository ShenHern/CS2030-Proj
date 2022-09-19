
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
            new Done(this.customer, 
                    this.server.returnUpdatedServer(this.timestamp + this.customer.getServeTime()), 
                    this.timestamp + this.customer.getServeTime()), 
            this.server.returnUpdatedServer(this.timestamp + this.customer.getServeTime())
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
    public String getType() {
        return "SERVE";
    }

    @Override
    public Server getServer () {
        return this.server;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.timestamp) + " " + this.customer.toString() + " served by " + this.server.toString();
    }
}

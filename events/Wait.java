package events;

import customers.Customer;
import interfaces.Event;
import misc.Pair;
import servers.Server;

public class Wait implements Event {
    private final Customer customer;
    private final Server server;
    private final double timestamp;
    private static final int PRIO = 0;
    
    /**
     * Creates instance of Wait Event.
     * @param customer  the customer on which the Event is performed
     * @param server    the server at which the customer waits at
     * @param timestamp the timestamp of the event
     */
    Wait(Customer customer, Server server, double timestamp) {
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
        return Wait.PRIO;
    }

    @Override
    public Pair<Event, Server> execute() {
        return new Pair<Event, Server>(new Serve(this.customer, 
            this.server.updateServerQueue(this.customer.getServeTime()), 
            this.server.getWaitUntil()), 
        this.server.updateServerQueue(this.customer.getServeTime())
        );
    }

    @Override
    public Event updateServer(Server server) {
        return new Wait(this.customer, this.server, this.timestamp);
    }

    @Override
    public boolean hasNextEvent() {
        return true;
    }

    @Override
    public String getType() {
        return "WAIT";
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.timestamp) + 
            " " + this.customer.toString() + 
            " waits at " + this.server.toString();
    }
}

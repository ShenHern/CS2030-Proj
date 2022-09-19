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
    public Pair<Event, Server>execute() {
        return new Pair<Event, Server>(new Serve(this.customer, this.server.updateServerQueue(), this.timestamp), this.server.updateServerQueue());
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
    public boolean isArrive() {
        return false;
    }

    @Override
    public String toString() {
        return this.timestamp + " " + this.customer.toString() + " waits at " + this.server.toString();
    }
}

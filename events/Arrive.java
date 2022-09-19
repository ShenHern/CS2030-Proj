package events;
import servers.Server;
import customers.Customer;
import interfaces.Event;
import misc.Pair;

public class Arrive implements Event {
    private final Customer customer;
    private final Server server;
    private static final int CANNOT_SERVE = -1;
    private final double timestamp;
    private static final int PRIO = -1;

    public Arrive(Customer customer, double timestamp) {
        this.customer = customer;
        this.timestamp = timestamp;
        this.server = new Server("", 0);
    }

    Arrive(Customer customer, Server server, double timestamp) {
        this.customer = customer;
        this.timestamp = timestamp;
        this.server = server;
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
        return Arrive.PRIO;
    }

    @Override
    public Pair<Event, Server> execute() {
        int serve = this.server.checkCanServe(this.customer);

        if (serve == CANNOT_SERVE) {
            if (this.server.checkCanWait()) {
                return new Pair<Event, Server>(new Wait(this.customer, this.server, this.timestamp), this.server);
            }
            return new Pair<Event, Server>(new Leave(this.customer, this.server, this.timestamp), this.server);
        }
        return new Pair<Event, Server>(new Serve(this.customer, this.server, this.timestamp), this.server);
    }

    @Override
    public boolean hasNextEvent() {
        return true;
    }

    @Override
    public boolean isArrive() {
        return true;
    }

    @Override
    public Event updateServer(Server server) {
        return new Arrive(this.customer, server, this.timestamp);
    }

    @Override
    public String toString() {
        return this.timestamp + " " + customer.toString() + " arrives";
    }
}

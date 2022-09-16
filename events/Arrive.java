package events;
import servers.Server;
import customers.Customer;
import interfaces.Event;
import misc.Pair;

public class Arrive implements Event {
    private final Customer customer;
    private static final int CANNOT_SERVE = -1;
    private final double timestamp;
    private static final int PRIO = -1;

    public Arrive(Customer customer, double timestamp) {
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
        return Arrive.PRIO;
    }

    @Override
    public Pair<Event, Server> execute(Server s) {
        int serve = s.checkCanServe(this.customer.getArrivalTime());

        if (serve == CANNOT_SERVE) {
            return new Pair<Event, Server>(new Leave(this.customer, this.timestamp), s);
        }
        return new Pair<Event, Server>(new Served(this.customer, s, this.timestamp), s);
    }

    @Override
    public boolean hasNextEvent() {
        return true;
    }

    @Override
    public String toString() {
        return this.timestamp + " " + customer.toString() + " arrives";
    }
}

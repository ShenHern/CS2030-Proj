public class Serve implements WaitableEvent {
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
    public double getWaitTime() {
        return this.timestamp - this.customer.getArrivalTime();
    }

    @Override
    public Pair<Event, Server> execute() {
        return new Pair<Event, Server>(
                new Done(this.customer,
                    this.server.updateServerBusyUntil(
                        this.timestamp + this.customer.getServeTime()),
                    this.timestamp + this.customer.getServeTime()),
            this.server.updateServerBusyUntil(this.timestamp + this.customer.getServeTime()));
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
    public Server getServer() {
        return this.server;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.timestamp) + " " +
                this.customer.toString() + " serves by " + this.server.toString();
    }
}

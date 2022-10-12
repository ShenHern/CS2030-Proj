public class Arrive implements Event {
    private final Customer customer;
    private final Server server;
    private final double timestamp;
    private static final int PRIO = -1;

    /**
     * Creates instance of Arrive Event.
     * @param customer customer instance that the Event is performed on
     * @param timestamp the timestamp of the event
     */
    public Arrive(Customer customer, double timestamp) {
        this.customer = customer;
        this.timestamp = timestamp;
        this.server = new Server("1", 0);
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
        if (!this.server.checkCanServe(this.customer)) {
            if (this.server.checkCanWait()) {
                return new Pair<Event, Server>(new Wait(this.customer, 
                                                        this.server, 
                                                        this.timestamp), 
                this.server);
            }
            return new Pair<Event, Server>(new Leave(this.customer, 
                                                    this.server, 
                                                    this.timestamp), 
            this.server);
        }
        return new Pair<Event, Server>(new Serve(this.customer, 
                                                this.server, 
                                                this.timestamp), 
        this.server);
    }

    @Override
    public boolean hasNextEvent() {
        return true;
    }

    @Override
    public String getType() {
        return "ARRIVE";
    }

    @Override
    public Event updateServer(Server server) {
        return new Arrive(this.customer, server, this.timestamp);
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.timestamp) + " " +
         customer.toString() + " arrives" + "\n";
    }
}

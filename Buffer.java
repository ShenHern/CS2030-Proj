class Buffer implements Event {
    private final Customer customer;
    private final Server server;
    private final double timestamp;
    private static final int PRIO = 1;

    Buffer(Customer customer, Server server, double timestamp) {
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
        return Buffer.PRIO;
    }

    @Override
    public Pair<Event, Server> execute() {
        return new Pair<Event, Server>(
            new Serve(this.customer, this.server, this.timestamp),
            this.server
        );
    }

    @Override
    public boolean hasNextEvent() {
        return true;
    }

    @Override
    public Event updateServer(Server server) {
        return new Buffer(this.customer, server, this.timestamp);
    }

    @Override
    public String getType() {
        return "BUFFER";
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public String toString() {
        return "";
    }
}
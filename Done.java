class Done implements Event {
    private final Customer customer;
    private final Server server;
    private final double timestamp;
    private static final int PRIO = 1;

    Done(Customer customer, Server server, double timestamp) {
        this.customer = customer;
        this.server = server;
        this.timestamp = timestamp;
    }

    public double getTimestamp() {
        return this.timestamp;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public int getPriority() {
        return Done.PRIO;
    }

    @Override
    public String toString() {
        return this.customer.toString() + " done serving by " + this.server.toString();
    }
}

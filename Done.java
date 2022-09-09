class Done implements Event {
    private final Customer customer;
    private final Server server;
    private final double timestamp;

    Done(Customer customer, Server server, double timestamp) {
        this.customer = customer;
        this.server = server;
        this.timestamp = timestamp;
    }

    @Override
    public double getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        return this.customer.toString() + " done serving by " + this.server.toString();
    }
}

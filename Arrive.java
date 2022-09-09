class Arrive implements Event {
    private final Customer customer;
    private static final int CANNOT_SERVE = -1;
    private final double timestamp;
    private static final int PRIO = -1;

    Arrive(Customer customer, double timestamp) {
        this.customer = customer;
        this.timestamp = timestamp;
    }

    public Event returnNextEvent(Server server) {
        int serve = server.checkCanServe(this.customer.getArrivalTime());

        if (serve == CANNOT_SERVE) {
            return new Leave(this.customer, this.timestamp);
        } else {
            return new Served(this.customer, server, this.timestamp);
        }
    }

    public double getTimestamp() {
        return this.timestamp;
    }

    public Customer getCustomer() {
        return this.customer;
    }
    
    public int getPriority() {
        return Arrive.PRIO;
    }

    @Override
    public String toString() {
        return customer.toString() + " arrives";
    }
}

class Arrive implements Event {
    private final Customer customer;
    private static final int CANNOT_SERVE = -1;

    Arrive(Customer customer) {
        this.customer = customer;
    }

    public Event returnNextEvent(Server server) {
        int serve = server.checkCanServe(customer.getArrivalTime());

        if (serve == CANNOT_SERVE) {
            return new Leave(this.customer);
        } else {
            return new Served(this.customer, server);
        }
    }
    
    @Override
    public String toString() {
        return customer.toString() + " arrives";
    }
}

class Arrive implements Event{
    private final Customer customer;
    private static final int CANNOT_SERVE = -1;

    Arrive(Customer customer) {
        this.customer = customer;
    }

    public String toString() {
        return customer.toString() + " arrives";
    }

    public Event returnNextEvent(Customer customer, Server server) {
        int serve = server.checkCanServe(customer.getArrivalTime());

        if (serve == CANNOT_SERVE) {
            return new Leave(customer);
        }

        else {
            return new Served(customer, server);
        }
    }
}

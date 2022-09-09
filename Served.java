public class Served implements Event {
    private final Customer customer;
    private final Server server;
    private final double timestamp;


    Served(Customer customer, Server server, double timestamp) {
        this.customer = customer;
        this.server = server;
        this.timestamp = timestamp;
    }

    public Done returnDoneEvent() {
        return new Done(customer, server, timestamp + customer.getServeTime());
    }

    public double getTimestamp() {
        return this.timestamp;
    }
    
    public Customer getCustomer() {
        return this.customer;
    }

    @Override
    public String toString() {
        return this.customer.toString() + " served by " + this.server.toString();
    }
}

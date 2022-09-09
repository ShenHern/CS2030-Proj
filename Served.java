public class Served implements Event {
    private final Customer customer;
    private final Server server;
    private final double timestamp;
    private static final int PRIO = 0;


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

    public int getPriority() {
        return Served.PRIO;
    }

    @Override
    public String toString() {
        return this.customer.toString() + " served by " + this.server.toString();
    }
}

public class Leave implements Event {
    private final Customer customer;
    private final double timestamp;
    private static final int PRIO = 0;

    
    Leave(Customer customer, double timestamp) {
        this.customer = customer;
        this.timestamp = timestamp;
    }

    public double getTimestamp() {
        return this.timestamp;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public int getPriority() {
        return Leave.PRIO;
    }

    @Override
    public String toString() {
        return customer.toString() + " leaves";
    }
}

public class Leave implements Event {
    private final Customer customer;
    private final double timestamp;

    
    Leave(Customer customer, double timestamp) {
        this.customer = customer;
        this.timestamp = timestamp;
    }

    public double getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        return customer.toString() + " leaves";
    }
}

public class Leave implements Event {
    private final Customer customer;

    Leave(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return customer.toString() + " leaves";
    }
}

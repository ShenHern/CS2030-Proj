public class Served implements Event {
    private final Customer customer;
    private final Server server;

    Served(Customer customer, Server server) {
        this.customer = customer;
        this.server = server;
    }


    @Override
    public String toString() {
        return this.customer.toString() + " " + this.server.toString();
    }
}

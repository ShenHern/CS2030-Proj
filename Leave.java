public class Leave implements Event {
    private final Customer customer;
    private final Server server;
    private final double timestamp;

    
    Leave(Customer customer, Server server, double timestamp) {
        this.customer = customer;
        this.server = server;
        this.timestamp = timestamp;
    }

    @Override
    public double getTimestamp() {
        return this.timestamp;
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }

    @Override
    public boolean hasNextEvent() {
        return false;
    }

    @Override
    public Pair<Event, ServerList> execute(ServerList serverList) {
        Server server = serverList.getServer(this.server.getIdx());
        return new Pair<Event, ServerList>(this, serverList.updateServer(server));
    }

    @Override
    public String getType() {
        return "LEAVE";
    }

    @Override
    public Event updateServer(Server server) {
        return new Leave(this.customer, server, this.timestamp);
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.timestamp) + " " + customer.toString() + " leaves" + "\n";
    }
}

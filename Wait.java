public class Wait implements Event {
    private final Customer customer;
    private final Server server;
    private final double timestamp;
    
    /**
     * Creates instance of Wait Event.
     * @param customer  the customer on which the Event is performed
     * @param server    the server at which the customer waits at
     * @param timestamp the timestamp of the event
     */
    Wait(Customer customer, Server server, double timestamp) {
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
    public Pair<Event, ServerList> execute(ServerList serverList) {
        Server server = serverList.getServer(this.server.getIdx());
        return new Pair<Event, ServerList>(
            new Buffer(this.customer, server, server.getBusyUntil()), 
            serverList.updateServer(server.updateServerQueue(this.customer))
        );
    }

    @Override
    public Event updateServer(Server server) {
        return new Wait(this.customer, server, this.timestamp);
    }

    @Override
    public boolean hasNextEvent() {
        return true;
    }

    @Override
    public String getType() {
        return "WAIT";
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.timestamp) + 
            " " + this.customer.toString() + 
            " waits at " + this.server.toString() + "\n";
    }
}

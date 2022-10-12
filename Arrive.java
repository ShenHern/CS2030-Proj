public class Arrive implements Event {
    private final Customer customer;
    private final Server server;
    private final double timestamp;
    private static final int PRIO = -1;

    /**
     * Creates instance of Arrive Event.
     * @param customer customer instance that the Event is performed on
     * @param timestamp the timestamp of the event
     */
    public Arrive(Customer customer, double timestamp) {
        this.customer = customer;
        this.timestamp = timestamp;
        this.server = new Server("1", 0);
    }

    Arrive(Customer customer, Server server, double timestamp) {
        this.customer = customer;
        this.timestamp = timestamp;
        this.server = server;
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
    public int getPriority() {
        return Arrive.PRIO;
    }

    @Override
    public Pair<Event, ServerList> execute(ServerList serverList) {
        Server server = serverList.getAvailableServer(this.customer)
            .orElseGet(() -> serverList.getQueueServer()
            .orElseGet(() -> serverList.getServer(0)));

        if (!server.checkCanServe(this.customer)) {
            if (server.checkCanWait()) {
                return new Pair<Event, ServerList>(new Wait(this.customer, 
                                                        server, 
                                                        this.timestamp), 
                serverList.updateServer(server));
            }
            return new Pair<Event, ServerList>(new Leave(this.customer, 
                                                    server, 
                                                    this.timestamp), 
            serverList.updateServer(server));
        }
        return new Pair<Event, ServerList>(new Serve(this.customer, 
                                                server, 
                                                this.timestamp), 
        serverList.updateServer(server));
    }

    @Override
    public boolean hasNextEvent() {
        return true;
    }

    @Override
    public String getType() {
        return "ARRIVE";
    }

    @Override
    public Event updateServer(Server server) {
        return new Arrive(this.customer, server, this.timestamp);
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.timestamp) + " " +
         customer.toString() + " arrives" + "\n";
    }
}

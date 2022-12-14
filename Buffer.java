import java.util.Optional;

class Buffer implements Event {
    private final Customer customer;
    private final Server server;
    private final double timestamp;

    Buffer(Customer customer, Server server, double timestamp) {
        this.customer = customer;
        this.server = server;
        this.timestamp = timestamp;
    }

    @Override
    public double getTimestamp() {
        return this.timestamp;
    }

    @Override
    public double getWaitTime() {
        return 0;
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }

    @Override
    public int customersServed() {
        return 0;
    }

    @Override
    public int customersLeft() {
        return 0;
    }

    @Override
    public Pair<Optional<Event>, ServerList> execute(ServerList serverList) {
        Server server = serverList.getServer(this.server.getIdx());
        if (server.checkCanServeQ(this.customer, this.timestamp)) {
            return new Pair<Optional<Event>, ServerList>(
                    Optional.of(new Serve(this.customer, server,
                            this.timestamp, server.getSelfCheck(timestamp))),
                    serverList.updateServer(server));
        }
        return new Pair<Optional<Event>, ServerList>(
                Optional.of(new Buffer(this.customer, server,
                        server.getBusyUntil())),
                serverList.updateServer(server));
    }

    @Override
    public boolean hasNextEvent() {
        return true;
    }

    @Override
    public Event updateServer(Server server) {
        return new Buffer(this.customer, server, this.timestamp);
    }

    @Override
    public String getType() {
        return "BUFFER";
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public String toString() {
        return "";
    }
}
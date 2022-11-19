import java.util.Optional;

public class Done implements Event {
    private final Customer customer;
    private final Server server;
    private final double timestamp;
    private final Optional<SelfCheckUnit> selfCheckU;

    Done(Customer customer, Server server, double timestamp, Optional<SelfCheckUnit> selfCheckU) {
        this.customer = customer;
        this.server = server;
        this.timestamp = timestamp;
        this.selfCheckU = selfCheckU;
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
    public double getWaitTime() {
        return 0;
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
        return new Pair<Optional<Event>, ServerList>(
                Optional.empty(),
                serverList.updateServer(server.updateServerBusyUntilRest()));
    }

    @Override
    public boolean hasNextEvent() {
        return false;
    }

    @Override
    public Event updateServer(Server server) {
        return new Done(this.customer, server, this.timestamp, this.selfCheckU);
    }

    @Override
    public String getType() {
        return "DONE";
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.timestamp) +
                " " + this.customer.toString() +
                " done serving by " + this.selfCheckU.map(x -> x.toString())
                        .orElse(this.server.toString())
                + "\n";
    }
}

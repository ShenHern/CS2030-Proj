import java.util.Optional;

public class Serve implements WaitableEvent {
    private final Customer customer;
    private final Server server;
    private final double timestamp;

    Serve(Customer customer, Server server, double timestamp) {
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
    public double getWaitTime() {
        return this.timestamp - this.customer.getArrivalTime();
    }

    @Override
    public Pair<Optional<Event>, ServerList> execute(ServerList serverList) {
        Server server = serverList.getServer(this.server.getIdx());
        Server updatedServer = server.updateServerBusyUntil(
            this.timestamp + this.customer.getServeTime());

        return new Pair<Optional<Event>, ServerList>(
                Optional.of(new Done(this.customer,
                    updatedServer,
                    this.timestamp + this.customer.getServeTime())),
            serverList.updateServer(updatedServer));
    }

    @Override
    public Event updateServer(Server server) {
        return new Serve(this.customer, server, this.timestamp);
    }

    @Override
    public boolean hasNextEvent() {
        return true;
    }

    @Override
    public String getType() {
        return "SERVE";
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.timestamp) + " " +
                this.customer.toString() + " serves by " + this.server.toString() + "\n";
    }
}

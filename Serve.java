import java.util.Optional;

public class Serve implements Event {
    private final Customer customer;
    private final Server server;
    private final double timestamp;
    private final Optional<SelfCheckUnit> selfCheckU;

    Serve(Customer customer, Server server, double timestamp, Optional<SelfCheckUnit> selfCheckU) {
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
        return this.timestamp - this.customer.getArrivalTime();
    }

    @Override
    public int customersServed() {
        return 1;
    }

    @Override 
    public int customersLeft() {
        return 0;
    }

    @Override
    public Pair<Optional<Event>, ServerList> execute(ServerList serverList) {
        Server server = serverList.getServer(this.server.getIdx());
        Server us = selfCheckU.map(
            x -> server.updateSelfCheck(
                x.updateBusyUntil(this.timestamp + this.customer.getServeTime())))
            .orElse(server.updateServerBusyUntil(
                this.timestamp + this.customer.getServeTime()));
        Optional<SelfCheckUnit> newSCU = selfCheckU.map(
            y -> y.updateBusyUntil(this.timestamp + this.customer.getServeTime()));

        return new Pair<Optional<Event>, ServerList>(
                Optional.of(new Done(this.customer,
                    us,
                    this.timestamp + this.customer.getServeTime(),
                    newSCU)),
            serverList.updateServer(us));
    }

    @Override
    public Event updateServer(Server server) {
        return new Serve(this.customer, server, this.timestamp, this.selfCheckU);
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
                this.customer.toString() + " serves by " + 
                this.selfCheckU.map(x -> x.toString())
                .orElse(this.server.toString()) + "\n";
    }
}

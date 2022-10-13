import java.util.Optional;

public interface Event {

    String toString();

    double getTimestamp();

    Customer getCustomer();
    
    Pair<Optional<Event>, ServerList> execute(ServerList serverList);

    boolean hasNextEvent();

    String getType();

    Server getServer();

    Event updateServer(Server server);

    double getWaitTime();

    int customersServed();

    int customersLeft();
}

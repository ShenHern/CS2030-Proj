package interfaces;
import customers.Customer;
import servers.Server;
import misc.Pair;

public interface Event {

    String toString();

    double getTimestamp();

    Customer getCustomer();
    
    int getPriority();

    Pair<Event, Server>execute();

    boolean hasNextEvent();

    boolean isArrive();

    boolean isWait();

    boolean isServe();

    boolean isLeave();

    Server getServer();

    Event updateServer(Server server);
}

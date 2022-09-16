package interfaces;
import customers.Customer;
import servers.Server;
import misc.Pair;

public interface Event {

    String toString();

    double getTimestamp();

    Customer getCustomer();
    
    int getPriority();

    Pair<Event, Server>execute(Server server);

    boolean hasNextEvent();


}

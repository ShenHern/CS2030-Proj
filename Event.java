public interface Event {

    String toString();

    double getTimestamp();

    Customer getCustomer();
    
    int getPriority();

    Pair<Event, Server> execute();

    boolean hasNextEvent();

    String getType();

    Server getServer();

    Event updateServer(Server server);
}

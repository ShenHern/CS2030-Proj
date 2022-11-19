import java.util.Optional;

interface Server {
    
    boolean checkCanServe(Customer customer);

    boolean checkCanServeQ(Customer customer, double timestame);

    Server updateServerBusyUntil(double newBusyUntil);

    Server updateServerBusyUntilRest();

    Server updateServerQueue(Customer customer);

    Optional<SelfCheckUnit> getSelfCheck(double timestamp);

    Server updateSelfCheck(SelfCheckUnit selfCheckUnit);

    int getIdx();

    int getQCurr();

    double getBusyUntil();

    Customer getNextCustomer();

    boolean checkCanWait();

    String toStringWait();
}
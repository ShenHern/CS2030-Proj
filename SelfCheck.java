import java.util.Optional;
import java.util.function.Supplier;

public class SelfCheck implements Server {

    private final double busyuntil; // indicates the lowest busy until in Self Check Cluster
    private final ImList<Customer> customerQueue;
    private final String name;
    private final int qmax;
    private final Supplier<Double> restTime;
    private final int qcurr;
    private final SelfCheckCluster selfCheckCluster;

    /**
     * Creates instance of Server.
     * 
     * @param name name of the server
     * @param qmax the maximum number of customers that can queue at the server
     */
    public SelfCheck(String name, int numOfSelfCheck, int qmax) {
        this.name = name;
        this.busyuntil = 0;
        this.qmax = qmax;
        this.restTime = () -> 0.0;
        this.qcurr = 0;
        this.customerQueue = new ImList<Customer>();
        this.selfCheckCluster = new SelfCheckCluster(Integer.parseInt(name), numOfSelfCheck);
    }

    /**
     * To create instance of server internally.
     * 
     * @param name      name of server
     * @param busyuntil the time the server is busy until while serving a customer
     * @param qmax      the maximum number of customers to that can queue at the
     *                  server
     * @param qcurr     the current number of customers in the queue
     * @param waituntil the time that a customer will have to wait until if it
     *                  queues up
     */
    private SelfCheck(String name, double busyuntil, int qmax, Supplier<Double> restTime, int qcurr,
            ImList<Customer> customerQueue, SelfCheckCluster selfCheckCluster) {
        this.name = name;
        this.busyuntil = busyuntil;
        this.qmax = qmax;
        this.restTime = restTime;
        this.qcurr = qcurr;
        this.customerQueue = customerQueue;
        this.selfCheckCluster = selfCheckCluster;
    }

    /**
     * Check if the server can serve a given customer.
     * 
     * @param customer The customer to check
     * @return true if customer can be served, false otherwise
     */
    public boolean checkCanServe(Customer customer) {
        double arriveTime = customer.getArrivalTime();
        if (Double.compare(arriveTime, busyuntil) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkCanServeQ(Customer customer, double timestamp) {
        return (customer.getNumber() == this.customerQueue.get(0).getNumber())
            && (timestamp == this.busyuntil);
    }

    public boolean checkCanWait() {
        return qcurr < qmax;
    }

    /**
     * method to update the busyUntil time - used in Serve Event.
     * 
     * @param newBusyUntil the new busyUntil of the server
     * @return the updated server after serving a customer
     */
    public Server updateServerBusyUntil(double newBusyUntil) {
        int newqcurr = 0;
        ImList<Customer> newCustomerQ = this.customerQueue;
        if (this.qcurr > 0) {
            newqcurr = this.qcurr - 1;
            newCustomerQ = newCustomerQ.remove(0);
        }

        return new SelfCheck(this.name, newBusyUntil, this.qmax,
            this.restTime, newqcurr, newCustomerQ, this.selfCheckCluster);
    }

    /**
     * method to update the busyUntil time when resting - used in Done Event.
     * 
     * @return the updated server after serving a customer
     */
    public Server updateServerBusyUntilRest() {
        return new SelfCheck(this.name, this.busyuntil + this.restTime.get(),
            this.qmax, this.restTime, this.qcurr, this.customerQueue, this.selfCheckCluster);
    }

    public Server updateServerQueue(Customer customer) {
        return new SelfCheck(this.name, this.busyuntil, this.qmax, this.restTime,
                this.qcurr + 1, this.customerQueue.add(customer), this.selfCheckCluster);
    }

    public Optional<SelfCheckUnit> getSelfCheck(double timestamp) {
        return Optional.of(selfCheckCluster.getAvailableUnit(timestamp));
    }

    /**
     * method to update update SelfCheck when a customer is served.
     * 
     * @param scu the self check unit to be updated in cluster
     * @return update SelfCheck after a customer is served
     */
    public Server updateSelfCheck(SelfCheckUnit scu) {
        int newqcurr = 0;
        ImList<Customer> newCustomerQ = this.customerQueue;
        if (this.qcurr > 0) {
            newqcurr = this.qcurr - 1;
            newCustomerQ = newCustomerQ.remove(0);
        }

        return new SelfCheck(this.name, this.selfCheckCluster.update(scu).lowestBU(),
                this.qmax, this.restTime,
                newqcurr, newCustomerQ, this.selfCheckCluster.update(scu));
    }

    /**
     * Check if able to add customer to queue.
     * 
     * @return true if can queue, false otherwise
     */

    public int getIdx() {
        return Integer.parseInt(this.name) - 1;
    }

    public int getQCurr() {
        return this.qcurr;
    }

    public double getBusyUntil() {
        return this.busyuntil;
    }

    public Customer getNextCustomer() {
        return this.customerQueue.get(0);
    }

    @Override
    public String toString() {
        return "self-check " + this.name;
    }

    public String toStringWait() {
        return "self-check " + this.name;
    }
}

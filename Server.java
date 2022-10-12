
public class Server {
    private final double busyuntil;
    private final ImList<Customer> customerQueue;
    private final String name;
    private final int qmax;
    private final int qcurr;

    /**
     * Creates instance of Server.
     * 
     * @param name name of the server
     * @param qmax the maximum number of customers that can queue at the server
     */
    public Server(String name, int qmax) {
        this.name = name;
        this.busyuntil = 0;
        this.qmax = qmax;
        this.qcurr = 0;
        this.customerQueue = new ImList<Customer>();
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
    private Server(String name, double busyuntil, int qmax, int qcurr,
            ImList<Customer> customerQueue) {
        this.name = name;
        this.busyuntil = busyuntil;
        this.qmax = qmax;
        this.qcurr = qcurr;
        this.customerQueue = customerQueue;
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

        return new Server(this.name, newBusyUntil, this.qmax, newqcurr, newCustomerQ);
    }

    public Server updateServerQueue(Customer customer) {
        return new Server(this.name, this.busyuntil, this.qmax,
                this.qcurr + 1, this.customerQueue.add(customer));
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
        return this.name;
    }
}

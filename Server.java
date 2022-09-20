

public class Server {
    private final double busyuntil;
    private final double waituntil;
    private final String name;
    private final int qmax;
    private final int qcurr;

    /**
     * Creates instance of Server.
     * @param name  name of the server
     * @param qmax  the maximum number of customers that can queue at the server
     */
    public Server(String name, int qmax) {
        this.name = name;
        this.busyuntil = 0;
        this.qmax = qmax;
        this.qcurr = 0;
        this.waituntil = 0;
    }

    /**
     * To create instance of server internally.
     * @param name  name of server
     * @param busyuntil the time the server is busy until while serving a customer
     * @param qmax  the maximum number of customers to that can queue at the server
     * @param qcurr the current number of customers in the queue
     * @param waituntil the time that a customer will have to wait until if it queues up
     */
    private Server(String name, double busyuntil, int qmax, int qcurr, double waituntil) {
        this.name = name;
        this.busyuntil = busyuntil;
        this.qmax = qmax;
        this.qcurr = qcurr;
        if (qcurr == 0) {
            this.waituntil = busyuntil;
        } else {
            this.waituntil = waituntil;
        }
    }
    
    /**
     * Check if the server can serve a given customer.
     * @param customer  The customer to check
     * @return  true if customer can be served, false otherwise
     */
    public boolean checkCanServe(Customer customer) {
        double arriveTime = customer.getArrivalTime();
        if (Double.compare(arriveTime, busyuntil) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkCanWait() {
        return qcurr < qmax;
    }

    // private Server updateServerBusyUntil(double serveTime) {
    //     int newqcurr = 0;
    //     if (this.qcurr > 0) {
    //         newqcurr = this.qcurr - 1;
    //     }
    //     return new Server(this.name, this.busyuntil + serveTime, this.qmax, newqcurr);
    // }

    private Server updateServerBusyUntil(double newBusyUntil) {
        int newqcurr = 0;
        if (this.qcurr > 0) {
            newqcurr = this.qcurr - 1;
        }
        return new Server(this.name, newBusyUntil, this.qmax, newqcurr, this.waituntil);
    }

    public Server returnUpdatedServer(double newBusyUntil) {
        return updateServerBusyUntil(newBusyUntil);
    }

    public Server updateServerQueue(double serveTime) {
        return new Server(this.name, this.busyuntil, this.qmax, 
            this.qcurr + 1, this.waituntil + serveTime);
    }

    public double getWaitUntil() {
        return this.waituntil;
    }

    /**
     * Check if able to add customer to queue.
     * @return true if can queue, false otherwise
     */
    public boolean canQueue() {
        if (this.qcurr < this.qmax) {
            return true;
        }
        return false;
    }

    public int getIdx() {
        return Integer.parseInt(this.name) - 1;
    }

    public double getBusyUntil() {
        return this.busyuntil;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

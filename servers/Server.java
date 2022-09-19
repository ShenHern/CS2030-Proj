package servers;
import customers.Customer;

public class Server {
    private final double busyuntil;
    private final String name;
    private final int qmax;
    private final int qcurr;
    private static final double EPSILON = 1e-15;
    private static final int CAN_SERVE_ARR_TIME_EQUAL = 1;
    private static final int CAN_SERVE_ARR_TIME_MORE_THAN = 0;
    private static final int CANNOT_SERVE = -1;

    public Server(String name, int qmax) {
        this.name = name;
        this.busyuntil = 0;
        this.qmax = qmax;
        this.qcurr = 0;
    }

    Server(String name, double busyuntil, int qmax, int qcurr) {
        this.name = name;
        this.busyuntil = busyuntil;
        this.qmax = qmax;
        this.qcurr = qcurr;
    }

    public int checkCanServe(double arriveTime) {
        if (Math.abs(arriveTime - busyuntil) <= EPSILON) {
            return CAN_SERVE_ARR_TIME_EQUAL;
        } else if (arriveTime + EPSILON > busyuntil) {
            return CAN_SERVE_ARR_TIME_MORE_THAN;
        } else {
            return CANNOT_SERVE;
        }
    }

    public boolean checkCanWait() {
        return qcurr < qmax;
    }

    private Server updateServerBusyUntil(double serveTime) {
        int newqcurr = 0;
        if (this.qcurr > 0) {
            newqcurr = this.qcurr - 1;
        }
        return new Server(this.name, this.busyuntil + serveTime, this.qmax, newqcurr);
    }

    private Server updateServerBusyUntil(double arriveTime, double serveTime) {
        int newqcurr = 0;
        if (this.qcurr > 0) {
            newqcurr = this.qcurr - 1;
        }
        return new Server(this.name, arriveTime + serveTime, this.qmax, newqcurr);
    }

    public Server returnUpdatedServer(Customer customer) {
        if (this.checkCanServe(customer.getArrivalTime()) == CAN_SERVE_ARR_TIME_EQUAL) {
            return this.updateServerBusyUntil(customer.getServeTime());
        } else if (this.checkCanServe(customer.getArrivalTime()) == CAN_SERVE_ARR_TIME_MORE_THAN) {
            return this.updateServerBusyUntil(customer.getArrivalTime(), customer.getServeTime());
        } else {
            return this;
        }
    }

    public Server updateServerQueue() {
        return new Server(this.name, this.busyuntil, this.qmax, this.qcurr + 1);
    }

    public boolean canQueue() {
        if (this.qcurr < this.qmax) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

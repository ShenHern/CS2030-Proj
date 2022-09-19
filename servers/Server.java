package servers;
import customers.Customer;

public class Server {
    private final double busyuntil;
    private final String name;
    private final int qmax;
    private final int qcurr;

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
        return new Server(this.name, newBusyUntil, this.qmax, newqcurr);
    }

    public Server returnUpdatedServer(double newBusyUntil) {
        return updateServerBusyUntil(newBusyUntil);
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

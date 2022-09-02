class Server {
    private final double busyuntil;
    private final String name;
    private static final double EPSILON = 1e-15;
    private static final int CAN_SERVE_ARR_TIME_EQUAL = 1;
    private static final int CAN_SERVE_ARR_TIME_MORE_THAN = 0;
    private static final int CANNOT_SERVE = -1;

    Server(String name) {
        this.name = name;
        this.busyuntil = 0;
    }

    Server(String name, double busyuntil) {
        this.name = name;
        this.busyuntil = busyuntil;
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

    private Server updateServerBusyUntil(double serveTime) {
        return new Server(this.name, this.busyuntil + serveTime);
    }

    private Server updateServerBusyUntil(double arriveTime, double serveTime) {
        return new Server(this.name, arriveTime + serveTime);
    }

    public Server returnUpdatedServer(Customer customer) {
        if (this.checkCanServe(customer.getArrivalTime()) == CAN_SERVE_ARR_TIME_EQUAL) {
            return this.updateServerBusyUntil(customer.getServeTime());
        } else if (this.checkCanServe(customer.getArrivalTime()) == CAN_SERVE_ARR_TIME_MORE_THAN) {
            return this.updateServerBusyUntil(customer.getArrivalTime(), customer.getServeTime());
        } else {
            return new Server(this.name, this.busyuntil);
        }
    }

    @Override
    public String toString() {
        return "served by " + this.name;
    }
}

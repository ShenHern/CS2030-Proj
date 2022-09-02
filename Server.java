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

    private int checkCanServe(double arr_time) {
        if (Math.abs(arr_time - busyuntil) <= EPSILON) {
            return CAN_SERVE_ARR_TIME_EQUAL;
        }

        else if(arr_time + EPSILON > busyuntil) {
            return CAN_SERVE_ARR_TIME_MORE_THAN;
        }
        
        else {
            return CANNOT_SERVE;
        }
    }

    private Server updateServerBusyUntil(double serv_time) {
        return new Server(this.name, this.busyuntil + serv_time);
    }

    private Server updateServerBusyUntil(double arr_time, double serv_time) {
        return new Server(this.name, arr_time + serv_time);
    }

    public String tryToServeCustomer(Customer customer) {
        if (this.checkCanServe(customer.getArrivalTime()) == CAN_SERVE_ARR_TIME_EQUAL || 
            this.checkCanServe(customer.getArrivalTime()) == CAN_SERVE_ARR_TIME_MORE_THAN) {
            return customer.toString() + this.toString();
        }

        else {
            return customer.toString() + "left the store";
        }
    }

    public Server returnUpdatedServer(Customer customer) {
        if (this.checkCanServe(customer.getArrivalTime()) == CAN_SERVE_ARR_TIME_EQUAL) {
            return this.updateServerBusyUntil(customer.getServeTime());
        }

        else if (this.checkCanServe(customer.getArrivalTime()) == CAN_SERVE_ARR_TIME_MORE_THAN) {
            return this.updateServerBusyUntil(customer.getArrivalTime(), customer.getServeTime());
        }

        else {
            return new Server(this.name, this.busyuntil);
        }
    }

    public String toString() {
        return "served by " + this.name;
    }
}

class Customer {
    private final int num;
    private final double arriveTime;
    private final double serveTime;

    Customer(int customerNum, double arriveTime, double serveTime) {
        this.num = customerNum;
        this.arriveTime = arriveTime;
        this.serveTime = serveTime;
    }

    public double getArrivalTime() {
        return arriveTime;
    }

    public double getServeTime() {
        return serveTime;
    }

    public String toString() {
        return "customer " + this.num;
    }

}


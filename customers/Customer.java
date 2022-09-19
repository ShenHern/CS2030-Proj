package customers;

public class Customer {
    private final int num;
    private final double arriveTime;
    private final double serveTime;

    /**
     * Creates instance of Customer
     * @param customerNum
     * @param arriveTime
     * @param serveTime
     */
    public Customer(int customerNum, double arriveTime, double serveTime) {
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

    public int getNumber() {
        return this.num;
    }

    public String toString() {
        return String.format("%d", this.num);
    }

}


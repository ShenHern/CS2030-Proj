class Customer {
    private final int num;
    private final double arr_time;
    private final double serv_time;

    Customer(int customer_num, double arr_time, double serv_time) {
        this.num = customer_num;
        this.arr_time = arr_time;
        this.serv_time = serv_time;
    }

    public double getArrivalTime() {
        return arr_time;
    }

    public double getServeTime() {
        return serv_time;
    }

    public String toString() {
        return "Customer " + this.num;
    }

}


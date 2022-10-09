public class StatCalc {
    private final double totalWaitTime;
    private final int customersServed;
    private final int customersLeft;


    StatCalc () {
        this.totalWaitTime = 0;
        this.customersServed = 0;
        this.customersLeft = 0;
    }

    StatCalc(double totalWaitTime, int customersServed, int customersLeft) {
        this.totalWaitTime = totalWaitTime;
        this.customersServed = customersServed;
        this.customersLeft = customersLeft;
    }

    public StatCalc updateStats(Event e) {
        if (e.getType() == "WAIT") {
            return new StatCalc(this.totalWaitTime + e.getServer().getWaitUntil() - e.getTimestamp(), this.customersServed, this.customersLeft);
        }
        //adding up num of customers served
        if (e.getType() == "SERVE") {
            return new StatCalc(this.totalWaitTime, this.customersServed + 1, this.customersLeft);
        }
        //adding up num of customers left
        if (e.getType() == "LEAVE") {
            return new StatCalc(this.totalWaitTime, this.customersServed, this.customersLeft + 1);
        }
        return this;
    }

    public double avgWaitTime() {
        return this.totalWaitTime / this.customersServed;
    }

    public int customersServed() {
        return this.customersServed;
    }

    public int customersLeft() {
        return this.customersLeft;
    }
}

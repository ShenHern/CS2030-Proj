public class StatCalc {
    private final double totalWaitTime;
    private final int customersServed;
    private final int customersLeft;

    StatCalc() {
        this.totalWaitTime = 0;
        this.customersServed = 0;
        this.customersLeft = 0;
    }

    StatCalc(double totalWaitTime, int customersServed, int customersLeft) {
        this.totalWaitTime = totalWaitTime;
        this.customersServed = customersServed;
        this.customersLeft = customersLeft;
    }

    /**
     * method that updates the simulation statistics after each event is processed.
     * 
     * @param e the event that was processed in the simulation
     * @return an updated StatCalc with the updated simulation statistics
     */
    public StatCalc updateStats(Event e) {
        // adding up wait time and num of customers served
        return new StatCalc(
            this.totalWaitTime + e.getWaitTime(),
            this.customersServed + e.customersServed(),
            this.customersLeft + e.customersLeft()
        );
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

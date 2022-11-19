public class SelfCheckUnit {
    private final double busyuntil;
    private final String name;
    private final int parent;

    SelfCheckUnit(String name, int parent) {
        this.name = name;
        this.busyuntil = 0;
        this.parent = parent;
    }

    private SelfCheckUnit(double busyuntil, String name, int parent) {
        this.busyuntil = busyuntil;
        this.name = name;
        this.parent = parent;
    }

    public boolean checkCanServe(double timestamp) {
        return timestamp >= busyuntil;
    }

    public double getBusyuntil() {
        return busyuntil;
    }

    public String getName() {
        return name;
    }

    public int getIdx() {
        return Integer.parseInt(this.name) - this.parent;
    }

    SelfCheckUnit updateBusyUntil(double timestamp) {
        return new SelfCheckUnit(timestamp, this.name, this.parent);
    }

    String toStringParent() {
        return "self-check " + this.parent;
    }

    @Override
    public String toString() {
        return "self-check " + this.name;
    }
}

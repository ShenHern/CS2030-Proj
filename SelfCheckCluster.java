public class SelfCheckCluster {
    private final ImList<SelfCheckUnit> cluster;

    SelfCheckCluster(int start, int numOfSelfChecks) {
        ImList<SelfCheckUnit> temp = new ImList<SelfCheckUnit>();
        //create cluster with starting number from start
        for (int i = start; i < start + numOfSelfChecks; i++) {
            SelfCheckUnit unit = new SelfCheckUnit(String.valueOf(i), start);
            temp = temp.add(unit);
        }
        this.cluster = temp;
    }

    private SelfCheckCluster(ImList<SelfCheckUnit> cluster) {
        this.cluster = cluster;
    }

    SelfCheckUnit getAvailableUnit(double timestamp) {
        for (SelfCheckUnit scu : this.cluster) {
            if (scu.checkCanServe(timestamp)) {
                return scu;
            }
        }
        return this.cluster.get(0);
    }

    double lowestBU() {
        double lowest = cluster.get(0).getBusyuntil();
        for (SelfCheckUnit scu : cluster) {
            if (scu.getBusyuntil() < lowest) {
                lowest = scu.getBusyuntil();
            }
        }
        return lowest;
    }

    SelfCheckCluster update(SelfCheckUnit scu) {
        return new SelfCheckCluster(this.cluster.set(scu.getIdx(), scu));
    }

}

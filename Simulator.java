public class Simulator {
    private final int numOfServers;
    private final int qmax;
    private final ImList<Pair<Double, Double>> inpTimes;
    

    Simulator(int numOfServers, int qmax, ImList<Pair<Double, Double>> inpTimes) {
        this.numOfServers = numOfServers;
        this.qmax = qmax;
        this.inpTimes = inpTimes;
    } 

    /**
     * Method to simulate the whole sequence of Events.
     * @return a String which represents the whole sequence of Events
     */

    public String simulate() {
        String logString = "";

        //add Arrive Events to PQ
        PQ<Event> pq = new PQ<Event>(new TimestampComp());
        pq = addArrives(pq);

        //create list of Servers
        ImList<Server> serverList = createServerList();

        //instantiating StatCalc
        StatCalc statCalc = new StatCalc();

        //execute events from PQ
        while (!pq.isEmpty()) {
            Pair<Event, PQ<Event>> pr = pq.poll();
            Event e = pr.first();
            pq = pr.second();
            //add e.toString to log
            logString += e.toString() + "\n";

            //process current event
            Pair<Event, Server> pr2 = processEvent(e, serverList);
            Event eNew = pr2.first();
            Server sNew = pr2.second();

            //update PQ with new Event
            if (e.hasNextEvent()) {
                pq = pq.add(eNew);
            }
            //update serverList with updated Server
            serverList = serverList.set(sNew.getIdx(), sNew);
            //calculating statistics
            statCalc = statCalc.updateStats(e);
        }

        //add statistics to log
        logString += String.format("[%.3f %d %d]", 
                statCalc.avgWaitTime(), 
                statCalc.customersServed(), 
                statCalc.customersLeft());

        return logString;
    }

    private PQ<Event> addArrives(PQ<Event> pq) {
        PQ<Event> newpq = pq;
        for (int i = 0; i < this.inpTimes.size(); i++) {
            Customer customer = new Customer(i + 1, this.inpTimes.get(i).first(), 
                                            this.inpTimes.get(i).second());

            Arrive a = new Arrive(customer, this.inpTimes.get(i).first());
            newpq = newpq.add(a);
        }
        return newpq;
    }
    
    private ImList<Server> createServerList() {
        ImList<Server> serverList = new ImList<Server>();
        for (int i = 0; i < numOfServers; i++) {
            Server s = new Server(String.valueOf(i + 1), this.qmax);
            serverList = serverList.add(s);
        }
        return serverList;
    }

    private Pair<Event, Server> processEvent(Event event, ImList<Server> serverList) {
        if (event.getType() == "ARRIVE") {
            //loop through servers to find idle server
            for (Server server : serverList) {
                if (server.checkCanServe(event.getCustomer())) {
                    event = event.updateServer(server);
                    return event.execute();
                }
            }
            //loop through servers to find server with available queue
            for (Server server : serverList) {
                if (server.checkCanWait()) {
                    event = event.updateServer(server);
                    return event.execute();
                }
            }
            //leave
            event = event.updateServer(serverList.get(0));
            return event.execute();
        }
        //execute any other event that is not Arrive
        event = event.updateServer(serverList.get(event.getServer().getIdx()));
        return event.execute();
    }
}

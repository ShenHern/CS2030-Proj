import customers.Customer;
import events.Arrive;
import interfaces.Event;
import misc.ImList;
import misc.PQ;
import misc.Pair;
import misc.TimestampComp;
import servers.Server;

public class Simulator {
    private final int numOfServers;
    private final int qmax;
    private final ImList<Pair<Double, Double>> inpTimes;
    

    Simulator(int numOfServers, int qmax, ImList<Pair<Double, Double>> inpTimes) {
        this.numOfServers = numOfServers;
        this.qmax = qmax;
        this.inpTimes = inpTimes;
    } 

    public String simulate() {
        String logString = "";

        //add Arrive Events to PQ
        PQ<Event> pq = new PQ<Event>(new TimestampComp());
        pq = addArrives(pq);

        //create list of Servers
        ImList<Server> serverList = createServerList();

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

        }
        //add statistics to log

        return logString;
    }

    private PQ<Event> addArrives(PQ<Event> pq) {
        PQ<Event> newpq = pq;
        for (int i = 0; i < this.inpTimes.size(); i++) {
            Customer customer = new Customer(i + 1, this.inpTimes.get(i).first(), this.inpTimes.get(i).second());
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
        if (event.isArrive()) {
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
            event = event.updateServer(serverList.get(0));
            return event.execute();
        }
        //execute any other event that is not Arrive
        return event.execute();
    }
}

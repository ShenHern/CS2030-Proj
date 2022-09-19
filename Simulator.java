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
    private static final int CANNOT_SERVE = -1;
    

    Simulator(int numOfServers, int qmax, ImList<Pair<Double, Double>> inpTimes) {
        this.numOfServers = numOfServers;
        this.qmax = qmax;
        this.inpTimes = inpTimes;
    } 

    public String simulate() {
        //add Arrive Events to PQ
        PQ<Event> pq = new PQ<Event>(new TimestampComp());
        pq = addArrives(pq);

        //create list of Servers
        ImList<Server> servers = createServerList();

        //execute events from PQ



        return "";
    }

    private PQ<Event> addArrives(PQ<Event> pq) {
        PQ<Event> newpq = pq;
        for (int i = 0; i < this.inpTimes.size(); i++) {
            Customer customer = new Customer(i, this.inpTimes.get(i).first(), this.inpTimes.get(i).second());
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
            for (Server server : serverList) {
                if (server.checkCanServe(event.getCustomer().getArrivalTime()) != CANNOT_SERVE) {
                    return new Pair<Event, Server>(event.execute(server).first(), event.execute(server).second());
                }
            }


        }
    }
}

import java.util.Scanner;
import java.io.File;
import customers.Customer;
import events.Arrive;
import interfaces.Event;
import misc.PQ;
import misc.Pair;
import misc.TimestampComp;
import servers.Server;

class Main {
    public static void main(String[] args) throws Exception {
        int count = 0;
        PQ<Event> pq = new PQ<Event>(new TimestampComp());
        File fin = new File("test.in");
        Scanner sc = new Scanner(fin);
        String name = sc.nextLine();
        Server server = new Server(name, 0);

        while (sc.hasNextDouble()) {
            count++;
            
            double arrivalTime = sc.nextDouble();
            double serviceTime = sc.nextDouble();
            
            Customer customer = new Customer(count, arrivalTime, serviceTime);
            Arrive a = new Arrive(customer, arrivalTime);
            pq = pq.add(a);
        }
        sc.close();

        while (!pq.isEmpty()) {
            Pair<Event, PQ<Event>> pr = pq.poll();
            Event e = pr.first();
            pq = pr.second();
            System.out.println(e.toString());
            Pair<Event, Server> pr2 = e.execute(server);
            Event newE = pr2.first();
            if (e.hasNextEvent()) {
                pq = pq.add(newE);
            }
            server = pr2.second();
        }
        
    }

}

import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        int count = 0;
        double timestamp = 0;
        PQ<Event> pq = new PQ<Event>(new TimestampComp());

        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        Server server = new Server(name);

        while (sc.hasNextDouble()) {
            count++;
            
            double arrivalTime = sc.nextDouble();
            double serviceTime = sc.nextDouble();
            
            timestamp = arrivalTime;

            Customer customer = new Customer(count, arrivalTime, serviceTime);
            Arrive a = new Arrive(customer, timestamp);
            pq = pq.add(a);
            Event e = a.returnNextEvent(server); //return a Leave or Served Event
            pq = pq.add(e);
            try {
                Served s = (Served) e;
                Done d = s.returnDoneEvent();
                pq = pq.add(d);
            } finally {}

            server = server.returnUpdatedServer(customer);
        }
        sc.close();

        while (!pq.isEmpty()) {
            Pair<Event, PQ<Event>> pr = pq.poll();
            Event e = pr.first();
            pq = pr.second();
            System.out.println(String.format("%f %s", e.getTimestamp(), e.toString()));
        }
        
    }

}

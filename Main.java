import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        int count = 0;

        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        Server server = new Server(name);

        while (sc.hasNextDouble()) {
            count++;
            double arrivalTime = sc.nextDouble();
            double serviceTime = sc.nextDouble();
            Customer customer = new Customer(count, arrivalTime, serviceTime);
            Arrive a = new Arrive(customer);
            System.out.println(a.toString());
            Event e = a.returnNextEvent(customer, server);
            System.out.println(e.toString());
            server = server.returnUpdatedServer(customer);
        }

        sc.close();
    }
}

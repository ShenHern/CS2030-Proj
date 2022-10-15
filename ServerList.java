import java.util.Optional;
import java.util.function.Supplier;

public class ServerList {

    private final ImList<Server> serverList;

    private ServerList(ImList<Server> serverList) {
        this.serverList = serverList;
    }

    ServerList(int numOfServers, int qmax, Supplier<Double> restTimes) {
        this.serverList = createServerList(numOfServers, qmax, restTimes);
    }

    /**
     * Get an available server if it exists.
     * 
     * @param customer the customer to serve
     * @return the available server if it exists, Optional.empty otherwise
     */
    public Optional<Server> getAvailableServer(Customer customer) {
        for (Server server : this.serverList) {
            if (server.checkCanServe(customer)) {
                return Optional.of(server);
            }
        }
        return Optional.empty();
    }

    /**
     * Get a server which a customer can queue at.
     * 
     * @return A server which still has space in its queue, Optional.empty otherwise
     */
    public Optional<Server> getQueueServer() {
        for (Server server : this.serverList) {
            if (server.checkCanWait()) {
                return Optional.of(server);
            }
        }
        return Optional.empty();
    }

    public Server getServer(int idx) {
        return this.serverList.get(idx);
    }

    public ServerList updateServer(Server server) {
        return new ServerList(this.serverList.set(server.getIdx(), server));
    }

    private static ImList<Server> createServerList(int numOfServers, int qmax,
        Supplier<Double> restTimes) {
        ImList<Server> serverList = new ImList<Server>();
        for (int i = 0; i < numOfServers; i++) {
            Server s = new Server(String.valueOf(i + 1), qmax, restTimes);
            serverList = serverList.add(s);
        }
        return serverList;
    }
}

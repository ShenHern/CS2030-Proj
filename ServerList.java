public class ServerList {
    /**
     * class to provide functionality for creating a ImList that is used in
     * Simulator class.
     * 
     * @param numOfServers the total number of servers generated in a simulation
     * @param qmax         the maximum number of customers that can queue at a given
     *                     server
     * @return an ImList of length {@code numOfServers} that contains
     *         servers with a maximum queue size of {@code qmax}
     */
    public static ImList<Server> createServerList(int numOfServers, int qmax) {
        ImList<Server> serverList = new ImList<Server>();
        for (int i = 0; i < numOfServers; i++) {
            Server s = new Server(String.valueOf(i + 1), qmax);
            serverList = serverList.add(s);
        }
        return serverList;
    }
}

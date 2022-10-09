public class ServerList {
    
    public static ImList<Server> createServerList(int numOfServers, int qmax) {
        ImList<Server> serverList = new ImList<Server>();
        for (int i = 0; i < numOfServers; i++) {
            Server s = new Server(String.valueOf(i + 1), qmax);
            serverList = serverList.add(s);
        }
        return serverList;
    }
}

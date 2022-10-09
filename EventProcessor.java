public class EventProcessor {
    /**
     * class to provide functionality for Event processing.
     * 
     * @param event      the current Event to be processed
     * @param serverList the list of Servers for a given simulation
     * @param pq         the current PQ
     * @return a Pair consisting of the updated PQ and ImList after
     *         processing an Event
     */
    public static Pair<PQ<Event>, ImList<Server>> processEvent(Event event,
            ImList<Server> serverList, PQ<Event> pq) {
        if (event.getType() == "ARRIVE") {
            // loop through servers to find idle server
            for (Server server : serverList) {
                if (server.checkCanServe(event.getCustomer())) {
                    event = event.updateServer(server);
                    return processUpdates(event, serverList, pq);
                }
            }
            // loop through servers to find server with available queue
            for (Server server : serverList) {
                if (server.checkCanWait()) {
                    event = event.updateServer(server);
                    return processUpdates(event, serverList, pq);
                }
            }
            // leave
            event = event.updateServer(serverList.get(0));
            return processUpdates(event, serverList, pq);
        }
        // execute any other event that is not Arrive
        event = event.updateServer(serverList.get(event.getServer().getIdx()));
        return processUpdates(event, serverList, pq);
    }

    private static Pair<PQ<Event>, ImList<Server>> processUpdates(Event event,
            ImList<Server> serverList,
            PQ<Event> pq) {
        Pair<Event, Server> pr = event.execute();
        Event eNew = pr.first();
        Server sNew = pr.second();

        // update PQ with new Event
        if (event.hasNextEvent()) {
            pq = pq.add(eNew);
        }

        // update serverList with update Server
        serverList = serverList.set(sNew.getIdx(), sNew);

        // return updated PQ<Event> and ImList<Server>
        return new Pair<PQ<Event>, ImList<Server>>(pq, serverList);
    }
}

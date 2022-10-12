public class EventProcessor {
    /**
     * class to provide functionality for Event processing.
     * 
     * @param event      the current Event to be processed
     * @param serverList the list of Servers for a given simulation
     * @param pq         the current PQ
     * @return a Pair consisting of the updated PQ and ServerList after
     *         processing an Event
     */
    public static Pair<PQ<Event>, ServerList> processEvent(Event event,
            ServerList serverList, PQ<Event> pq) {
        return processUpdates(event, serverList, pq);
    }

    private static Pair<PQ<Event>, ServerList> processUpdates(Event event,
            ServerList serverList,
            PQ<Event> pq) {
        Pair<Event, ServerList> pr = event.execute(serverList);
        Event eNew = pr.first();
        ServerList sListNew = pr.second();

        // update PQ with new Event
        if (event.hasNextEvent()) {
            pq = pq.add(eNew);
        }

        // return updated PQ<Event> and ServerList
        return new Pair<PQ<Event>, ServerList>(pq, sListNew);
    }
}

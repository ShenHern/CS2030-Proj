

import java.util.Comparator;

public class TimestampComp implements Comparator<Event> {

    /**
     * Method to compare the order in which Events are polled from the PQ.
     * @return the order in which Events are polled from the PQ
     */
    public int compare(Event event1, Event event2) {
        if (Double.compare(event1.getTimestamp(), event2.getTimestamp()) == 0) {
            //if timestamp of events is equal
            if (event1.getCustomer().getNumber() - event2.getCustomer().getNumber() == 0) {
                //handle for events pertaining to same customer;
                //ie priority of events is (Arrive -> Served/Leave -> Done) for a given customer
                return event1.getPriority() - event2.getPriority();
            }
            //sort based on order of customer arrival
            return event1.getCustomer().getNumber() - event2.getCustomer().getNumber();
        }

        return Double.compare(event1.getTimestamp(), event2.getTimestamp());
    }
}
import java.util.Comparator;

class TimestampComp implements Comparator<Event> {
    private static final double EPSILON = 1e-15;
    public int compare(Event event1, Event event2) {
        if (Math.abs(event1.getTimestamp() - event2.getTimestamp()) < EPSILON){
            if (event1.getCustomer().getNumber() - event2.getCustomer().getNumber() == 0) {
                //handle for events pertaining to same customer;
                //ie priority of events is (Arrive > Served/Leave > Done) for a given customer
                return event1.getPriority() - event2.getPriority();
            }
            //otherwise if timestamp of events is equal then we sort based on order of customer arrival
            return event1.getCustomer().getNumber() - event2.getCustomer().getNumber();
        } else if (event1.getTimestamp() - event2.getTimestamp() + EPSILON > 0) {
            return 1;
        }
        return -1;
    }
}
import java.util.Comparator;

class EventComparator implements Comparator<Event> {
    public int compare(Event e1, Event e2) {

        if (e1.getTime() - e2.getTime() < 0) {
            return -1;
        } else if (e1.getTime() - e2.getTime() > 0) {
            return 1;
        } else {
            return (e1.getCustomerID() - e2.getCustomerID());
        }
    }
}

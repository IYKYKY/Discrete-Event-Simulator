interface Event {

    int getCustomerID();

    double getTime();

    double getWaitingTime();

    boolean endEvent();

    Pair<Pair<Event, String>, ImList<Server>> processEvent(ImList<Server> servers);

    Pair<Integer, Integer> update(Pair<Integer, Integer> temp);
}

class RestEvent implements Event {
    private final Customer customer;
    private final int serverID;
    private final double occurTime;

    RestEvent(Customer customer, int serverID, double occurTime) {
        this.customer = customer;
        this.serverID = serverID;
        this.occurTime = occurTime;
    }

    public int getCustomerID() {
        return this.customer.getID();
    }

    public double getTime() {
        return this.occurTime;
    }
    
    public double getWaitingTime() {
        return 0.0;
    }

    public Pair<Pair<Event, String>, ImList<Server>> processEvent(ImList<Server> servers) {
        String res = "";
        return new Pair<Pair<Event,String>, ImList<Server>>(
            new Pair<Event, String>(this, res), servers);
    }

    public Pair<Integer, Integer> update(Pair<Integer, Integer> temp) {
        temp = new Pair<Integer, Integer>(temp.first(), temp.second());
        return temp;  
    }

    public boolean endEvent() {
        return true;
    }
}

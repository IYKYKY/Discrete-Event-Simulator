class LeaveEvent implements Event {
    private final Customer customer;
    private final double occurTime;

    LeaveEvent(Customer customer, double occurTime) {
        this.customer = customer;
        this.occurTime = occurTime;
    } 

    public Pair<Pair<Event, String>, ImList<Server>> processEvent(ImList<Server> servers) {    
        String res = String.format("%.3f %d leaves\n", occurTime, customer.getID());
        return new Pair<Pair<Event, String>, ImList<Server>>(new 
            Pair<Event, String>(this, res), servers);
    }

    public Pair<Integer, Integer> update(Pair<Integer, Integer> temp) {
        temp = new Pair<Integer, Integer>(temp.first(), temp.second() + 1);
        return temp;
    }    

    public double getWaitingTime() {
        return 0.0;
    }    

    public int getCustomerID() {        
        return this.customer.getID();
    }

    public double getTime() {
        return this.occurTime;
    }

    public boolean endEvent() {
        return true;
    }

}

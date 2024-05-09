class DoneEvent implements Event {
    private final Customer customer;
    private final Server server;
    private final int serverID;
    private final double occurTime;
    private final double waitingTime;

    DoneEvent(Customer customer, Server server, int serverID, 
        double occurTime, double waitingTime) {
        this.customer = customer;
        this.server = server;
        this.serverID = serverID;
        this.occurTime = occurTime;
        this.waitingTime = waitingTime;
    } 

    public Pair<Pair<Event, String>, ImList<Server>> processEvent(ImList<Server> servers) {
        Server s = servers.get(serverID - 1);
        String res = "";

        if (s.isScc()) {
            res += String.format("%.3f %d done serving by self-check %d\n",
                occurTime, customer.getID(), serverID);
            return new Pair<Pair<Event, String>, ImList<Server>>(new 
                Pair<Event, String>(this, res), servers);
        } else {
            double restingTime = s.getRestingTime();
            Server newS = s.updateTime(restingTime);
            servers = servers.set(serverID - 1, newS);
            res += String.format("%.3f %d done serving by %d\n", occurTime, 
            customer.getID(), serverID);
            return new Pair<Pair<Event, String>, ImList<Server>>(new
                Pair<Event, String>(new RestEvent(this.customer, 
                this.serverID, this.occurTime), res),servers);
        }
    }

    public Pair<Integer, Integer> update(Pair<Integer, Integer> temp) {
        temp = new Pair<Integer, Integer>(temp.first() + 1, temp.second());
        return temp;
    }
    
    public double getWaitingTime() {
        return this.waitingTime;
    }

    public double getRestTime() {
        return 0.0;
    }
    
    public int getCustomerID() {
        return this.customer.getID();
    }

    public double getTime() {
        return this.occurTime;
    }

    public String toString() {
        return String.format("%.3f %d done serving by %d\n", 
            occurTime, this.customer.getID(), serverID);
    }

    public boolean endEvent() {
        if (server.isScc()) {
            return true;
        } else {
            return false;
        }
    }
}

class WaitEvent implements Event {
    private final Customer customer;
    private final int serverID;
    private final double occurTime;

    WaitEvent(Customer customer, int serverID, double occurTime) {
        this.customer = customer;
        this.serverID = serverID;
        this.occurTime = occurTime;
    }

    public Pair<Pair<Event, String>, ImList<Server>> processEvent(ImList<Server> servers) {
        Server s = servers.get(serverID - 1);
        Event newEvent;
        String res = "";
        if (s.isScc()) {
            int index = serverID - 1;
            double nextMin = servers.get(index).getAvailableTime();

            for (int i = serverID - 1; i < servers.size(); i++) {
                if (servers.get(i).getAvailableTime() < nextMin) {
                    nextMin = servers.get(i).getAvailableTime();
                    index = i;
                }
            }

            if (customer.getQueueNo() == 1) {
                newEvent = new ServeEvent(customer, index + 1, 
                    serverID, nextMin);
                return new Pair<Pair<Event, String>, ImList<Server>>(new 
                    Pair<Event, String>(newEvent, res), servers);
            } else if (customer.getQueueNo() > 1) {
                Customer newC = customer.moveForward();
                newEvent = new WaitEvent(newC, serverID, nextMin);
                return new Pair<Pair<Event, String>, ImList<Server>>(new
                    Pair<Event, String>(newEvent, res), servers);
            } else {
                return new Pair<Pair<Event, String>, ImList<Server>>(new 
                    Pair<Event, String>(this, res), servers);
            }
        } else {
            if (s.getAvailableTime() > this.occurTime) {
                newEvent = new WaitEvent(customer, serverID, s.getAvailableTime());
                return new Pair<Pair<Event, String>, ImList<Server>>(new
                    Pair<Event, String>(newEvent, res), servers);
            }
            if (customer.getQueueNo() == 1) {
                newEvent = new ServeEvent(customer, serverID, serverID, s.getAvailableTime());
                return new Pair<Pair<Event, String>, ImList<Server>>(new 
                    Pair<Event, String>(newEvent, res), servers);
            } else if (customer.getQueueNo() > 1) {
                Customer newC = customer.moveForward();
                newEvent = new WaitEvent(newC, serverID, s.getAvailableTime());
                return new Pair<Pair<Event, String>, ImList<Server>>(new
                    Pair<Event, String>(newEvent, res), servers);
            } else {
                return new Pair<Pair<Event, String>, ImList<Server>>(new 
                    Pair<Event, String>(this, res), servers);
            }
        }
    }
  
    public Pair<Integer, Integer> update(Pair<Integer, Integer> temp) {
        temp = new Pair<Integer, Integer>(temp.first(), temp.second());
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
        return false;
    }
}

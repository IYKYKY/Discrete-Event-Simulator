class ServeEvent implements Event {

    private final Customer customer;
    private final int serverID;
    private final int queueID;
    private final double occurTime;

    ServeEvent(Customer customer, int serverID, int queueID, double occurTime) {
        this.customer = customer;
        this.serverID = serverID;
        this.queueID = queueID;
        this.occurTime = occurTime;
    }


    public Pair<Pair<Event, String>, ImList<Server>> processEvent(ImList<Server> servers) {
        double serveTime = customer.getServiceTime();
        Server s = servers.get(serverID - 1);
        double newTime = this.occurTime + serveTime;
        ImList<Customer> queue = servers.get(queueID - 1).getQueue();
        double waitingTime = 0.0;
        Event newEvent;
        String res = "";

        if (s.isScc()) {
            res += String.format("%.3f %d serves by self-check %d\n",
                occurTime, customer.getID(), serverID);
        } else {
            res += String.format("%.3f %d serves by %d\n", occurTime, 
                customer.getID(), serverID);
        }

        waitingTime += this.occurTime - customer.getArrivalTime();
        if (queue.size() != 0) {
            queue = queue.remove(0);
        }
        
        if (serverID == queueID) {
            Server newS = s.updateQueue(queue);
            newS = newS.updateTime(newTime - s.getAvailableTime());
            servers = servers.set(serverID - 1, newS);
            newEvent = new DoneEvent(customer, newS, serverID, 
                newTime, waitingTime);
        } else {
            Server anotherS = servers.get(queueID - 1);
            anotherS = anotherS.updateQueue(queue);
            s = s.updateTime(newTime - s.getAvailableTime());
            servers = servers.set(serverID - 1, s);
            servers = servers.set(queueID - 1, anotherS);
            newEvent = new DoneEvent(customer, anotherS, serverID, 
                newTime, waitingTime);
        }
        
        return new Pair<Pair<Event, String>, ImList<Server>>(new 
            Pair<Event, String>(newEvent, res), servers);
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

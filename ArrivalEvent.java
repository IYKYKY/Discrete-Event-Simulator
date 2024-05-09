class ArrivalEvent implements Event {
    private final Customer customer;
    private final int qmax;
    private final double occurTime;

    ArrivalEvent(Customer customer, int qmax, double occurTime) {
        this.customer = customer;
        this.qmax = qmax;
        this.occurTime = occurTime;
    }

    private int findAvailableServer(ImList<Server> servers) {
        for (int i = 0; i < servers.size(); i++) {
            if (servers.get(i).getAvailableTime() <= customer.getArrivalTime()) {    
                return i;
            }
        }
        return -1;
    }

    private int findAvailableQueue(ImList<Server> servers, int qmax) {
        for (int i = 0; i < servers.size(); i++) {
            int index = 0;
            if (!servers.get(i).isScc() && servers.get(i).checkQueue(qmax)) {
                return i;
            } else if (servers.get(i).isScc() && 
                servers.get(i).isFirst() && 
                servers.get(i).checkQueue(qmax)) {
                index = i;
                return i;
            } else if (servers.get(i).isScc() && 
                !servers.get(i).isFirst() && 
                servers.get(index).checkQueue(qmax)) {
                return index;
            }
        }
        return -1;
    }

    private int findFirstScc(ImList<Server> servers) {
        for (int i = 0; i < servers.size(); i++) {
            if (servers.get(i).isFirst()) {
                return i;
            }
        }
        return -1;
    }

    public Pair<Pair<Event, String>, ImList<Server>> 
        processEvent(ImList<Server> servers) {

        int availableServer = findAvailableServer(servers);
        int availableQueue = findAvailableQueue(servers, qmax);
        Event newEvent;
        String res = String.format("%.3f %d arrives\n", this.occurTime, 
            this.customer.getID());

        if (availableServer != -1) {
            newEvent = new ServeEvent(customer, availableServer + 1, 
                availableServer + 1, occurTime);
            return new Pair<Pair<Event, String>, ImList<Server>>(new 
                Pair<Event, String>(newEvent, res), servers);
        } else if (availableQueue != -1) {
            Server s = servers.get(availableQueue);
            if (s.isScc()) {
                int index = findFirstScc(servers);
                ImList<Customer> queue = servers.get(index).getQueue();
                queue = queue.add(customer);
                Customer newC = customer.addToQueue(queue.size());
                Server newS = s.updateQueue(queue);
                servers = servers.set(index, newS);
                res += String.format("%.3f %d waits at self-check %d\n",
                    occurTime, customer.getID(), index + 1);
                newEvent = new WaitEvent(newC, availableQueue + 1, occurTime);
            } else {
                ImList<Customer> queue = s.getQueue();
                queue = queue.add(customer);
                Customer newC = customer.addToQueue(queue.size());
                Server newS = s.updateQueue(queue);
                servers = servers.set(availableQueue, newS);
                res += String.format("%.3f %d waits at %d\n", occurTime, 
                customer.getID(), availableQueue + 1);
                newEvent = new WaitEvent(newC, availableQueue + 1, occurTime);
            }
            return new Pair<Pair<Event, String>, ImList<Server>>(new
                Pair<Event, String>(newEvent, res), servers);
        } else {
            newEvent = new LeaveEvent(customer, occurTime);
            return new Pair<Pair<Event, String>, ImList<Server>>(new 
                Pair<Event, String>(newEvent, res), servers);
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

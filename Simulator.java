import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int numOfSelfChecks;
    private final int qmax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTimes;
    private final Supplier<Double> restTimes;

    public Simulator(int numOfServers, int numOfSelfChecks, int qmax, 
        ImList<Double> arrivalTimes, Supplier<Double> serviceTimes, Supplier<Double> restTimes) {
        this.numOfServers = numOfServers;
        this.numOfSelfChecks = numOfSelfChecks;
        this.qmax = qmax;
        this.arrivalTimes = arrivalTimes;
        this.serviceTimes = serviceTimes;
        this.restTimes = restTimes;
    }

    private ImList<Server> initServers() {
        ImList<Server> servers = new ImList<Server>();

        for (int i = 0; i < this.numOfServers; i++) {
            ImList<Customer> queue = new ImList<Customer>();
            servers = servers.add(new Server(i + 1, 0.0, queue, restTimes));
        }

        for (int j = this.numOfServers; j < this.numOfSelfChecks + this.numOfServers; j++) {
            ImList<Customer> queue = new ImList<Customer>();
            if (j == this.numOfServers) {
                servers = servers.add(new SelfCheckout(j + 1, 0, queue, 
                    restTimes, true));
            } else {
                servers = servers.add(new SelfCheckout(j + 1, 0, queue,
                    restTimes, false));
            }
        }
        return servers;
    }

    public String simulate() {
        String output = "";
        ImList<Server> servers = this.initServers();
        Supplier<Double> serviceTime = this.serviceTimes;
        PQ<Event> pq = new PQ<Event>(new EventComparator());
        Pair<Integer, Integer> temp = new Pair<Integer, Integer>(0, 0);
        double waitingTime = 0.0;
        double avgWaitingTime = 0.0;
        boolean endEvent = false;

        for (int i = 0; i < this.arrivalTimes.size(); i++) {
            pq = pq.add(new ArrivalEvent(new Customer(i + 1, 
                this.arrivalTimes.get(i), serviceTime, 0), this.qmax,
                this.arrivalTimes.get(i)));
        }

        while (!pq.isEmpty()) {
            Pair<Event, PQ<Event>> helper = pq.poll();
            Event currentEvent = helper.first(); 

            // System.out.println(currentEvent);
            // System.out.println(pq);
            temp = currentEvent.update(temp);
            pq = helper.second();
            waitingTime += currentEvent.getWaitingTime();
            // System.out.println(waitingTime);

            endEvent = currentEvent.endEvent();
            Pair<Pair<Event, String>, ImList<Server>> tempPair =
                currentEvent.processEvent(servers);
            Pair<Event, String> renew = tempPair.first();
            output = output + renew.second();
            servers = tempPair.second();

            if (endEvent) {
                continue;
            }

            // System.out.println(renew.second());
            pq = pq.add(renew.first());
        }

        if (temp.first() != 0) {
            avgWaitingTime = waitingTime / temp.first();
        } 
        String count = String.format("[%.3f %d %d]", avgWaitingTime, temp.first(), temp.second());
        return output + count;
    }
}

import java.util.function.Supplier;

class Server {
    private final int serverID;
    private final double availableTime;
    private final ImList<Customer> queue;
    private final Supplier<Double> restTime;

    Server(int serverID, double availableTime, ImList<Customer> queue, 
        Supplier<Double> restTime) {
        this.serverID = serverID;
        this.availableTime = availableTime;
        this.queue = queue;
        this.restTime = restTime;
    }

    public Server addCustomer(Customer c) {
        ImList<Customer> newQueue = this.getQueue().add(c);
        return new Server(this.serverID, this.availableTime, newQueue, 
            this.restTime);
    }

    public int getID() {
        return this.serverID;
    }

    public double getAvailableTime() {
        return this.availableTime;
    }

    public double getRestingTime() {
        return this.restTime.get();
    }

    public Supplier<Double> getRest() {
        return this.restTime;
    }

    public ImList<Customer> getQueue() {
        return this.queue;
    }

    public Server updateTime(double time) {
        return new Server(this.serverID, this.availableTime + time,
            this.queue, this.restTime);
    }

    public Server updateQueue(ImList<Customer> newQueue) {
        return new Server(this.serverID, this.availableTime, newQueue, 
            this.restTime);
    }

    public boolean isFirst() {
        return false;
    }

    public boolean isScc() {
        return false;
    }

    public boolean checkQueue(int qmax) {
        return (this.queue.size() < qmax);
    }

}

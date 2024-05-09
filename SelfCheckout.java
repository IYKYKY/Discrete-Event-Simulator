import java.util.function.Supplier;

class SelfCheckout extends Server {

    private final boolean firstScc;

    SelfCheckout(int serverID, double time, ImList<Customer> queue, 
        Supplier<Double> restTime, boolean firstScc) {
            
        super(serverID, time, queue, restTime);
            
        this.firstScc = firstScc;
    }

    public boolean isScc() {
        return true;
    }
    
    public boolean isFirst() {
        return this.firstScc;
    }

    public SelfCheckout updateTime(double t) {
        return new SelfCheckout(this.getID(), this.getAvailableTime() + t, 
            this.getQueue(), this.getRest(), this.firstScc);
    }

    public SelfCheckout updateQueue(ImList<Customer> newQueue) {
        return new SelfCheckout(this.getID(), this.getAvailableTime(), newQueue,
            this.getRest(), this.firstScc);
    }

}

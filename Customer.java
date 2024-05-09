import java.util.function.Supplier;

class Customer {
    private final int customerID;
    private final double arrivalTime;
    private final Supplier<Double> serviceTime;
    private final int queueNo;
    
    Customer(int customerID, double arrivalTime, 
        Supplier<Double> serviceTime, int queueNo) {
        this.customerID = customerID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.queueNo = queueNo;
    }

    public int getID() {
        return this.customerID;
    }

    public Customer addToQueue(int num) {
        return new Customer(this.customerID, this.arrivalTime, 
            this.serviceTime, num + this.queueNo);
    }

    public Customer moveForward() {
        return new Customer(this.customerID, this.arrivalTime, 
            this.serviceTime, this.queueNo - 1);
    }

    public int getQueueNo() {
        return this.queueNo;
    }

    public double getServiceTime() {
        return this.serviceTime.get();
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }
}
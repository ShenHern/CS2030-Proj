interface Event {

    public String toString();

    public double getTimestamp();

    public Customer getCustomer();
    
    public int getPriority();
}

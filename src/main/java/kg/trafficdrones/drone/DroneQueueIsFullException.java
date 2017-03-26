package kg.trafficdrones.drone;

public class DroneQueueIsFullException extends RuntimeException {
    public DroneQueueIsFullException(Integer capacity) {
        super("Cannot add more elements to full queue. Queue capacity=" + capacity);
    }
}

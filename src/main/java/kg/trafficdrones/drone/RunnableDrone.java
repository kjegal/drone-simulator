package kg.trafficdrones.drone;

import kg.trafficdrones.logger.EventLogger;

public class RunnableDrone implements Runnable {

    private final EventLogger eventLogger = new EventLogger();

    private final Drone drone;

    RunnableDrone(Drone drone) {
        this.drone = drone;
    }

    @Override
    public void run() {
        try {
            while (true) {
                drone.refreshRoutes();
                drone.move();
                Thread.sleep(5);
            }
        } catch (InterruptedException e) {
            eventLogger.shutdownReceived(drone);
        }
    }
}
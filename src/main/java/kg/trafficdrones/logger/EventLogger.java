package kg.trafficdrones.logger;

import kg.trafficdrones.drone.Drone;
import kg.trafficdrones.drone.Report;
import kg.trafficdrones.route.Route;
import kg.trafficdrones.station.TubeStation;

public class EventLogger {
    private static final ConsoleLogger logger = new ConsoleLogger();

    public void publish(String route) {
        logger.log("Published: " + route);
    }

    public void report(Drone drone, TubeStation tubeStation, Report report) {
        logger.log("Drone " + drone.getId() + " reporting near " + tubeStation.getName() + ": " + report);
    }

    public void consume(Drone drone, Route route) {
        logger.log("Drone " + drone.getId() + " consumed: " + route);
    }

    public void shutdownBroadcasted() {
        logger.log("SHUTDOWN NOW!");
    }

    public void shutdownReceived(Drone drone) {
        logger.log("Drone " + drone.getId() + " is shutting down");
    }

    public void start() {
        logger.log("Drone simulator starting");
    }

    public void finish() {
        logger.log("Drone simulator exiting");
    }
}

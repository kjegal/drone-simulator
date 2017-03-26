package kg.trafficdrones.drone;

import kg.trafficdrones.queue.PositionBroker;
import kg.trafficdrones.route.Coordinate;
import kg.trafficdrones.station.Locator;
import kg.trafficdrones.station.TubeStation;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DroneService {
    private static final Coordinate START_POSITION =
            new Coordinate(new BigDecimal("51.476105"), new BigDecimal("-0.100224"));

    private final PositionBroker messageBroker;

    private final Locator<TubeStation> tubeStationLocator;

    public DroneService(PositionBroker messageBroker, Locator<TubeStation> tubeStationLocator) {
        this.messageBroker = messageBroker;
        this.tubeStationLocator = tubeStationLocator;
    }

    public ExecutorService startDrone(Integer id) {
        Drone drone = new TrafficDrone(id, () -> messageBroker.consume(id), tubeStationLocator, START_POSITION);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new RunnableDrone(drone));
        return executor;
    }
}

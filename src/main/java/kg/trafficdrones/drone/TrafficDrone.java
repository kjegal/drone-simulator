package kg.trafficdrones.drone;

import kg.trafficdrones.logger.EventLogger;
import kg.trafficdrones.route.Coordinate;
import kg.trafficdrones.route.Route;
import kg.trafficdrones.station.Locator;
import kg.trafficdrones.station.TrafficCondition;
import kg.trafficdrones.station.TubeStation;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Supplier;

import static kg.trafficdrones.station.TrafficCondition.*;

public class TrafficDrone implements Drone {
    private final static EventLogger logger = new EventLogger();
    private final Integer id;
    private final Supplier<Optional<Route>> routeSupplier;
    private final Locator<TubeStation> stationLocator;
    private DronePosition position;
    private FixedSizeQueue<Route> routes = new FixedSizeQueue<>(10);

    TrafficDrone(Integer id, Supplier<Optional<Route>> routeSupplier, Locator<TubeStation> stationLocator,
                 Coordinate position) {
        this.id = id;
        this.routeSupplier = routeSupplier;
        this.stationLocator = stationLocator;
        this.position = DronePosition.from(position);
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void refreshRoutes() {
        if (routes.hasCapacity()) {
            routeSupplier.get().ifPresent(route -> {
                logger.consume(this, route);
                routes.add(route);
            });
        }
    }

    @Override
    public void move() {
        if (position.hasArrived()) {
            report();
            routes.poll().ifPresent(route -> position = position.changeDestination(route.getPosition()));
        } else {
            position = position.move();
        }
    }

    private void report() {
        stationLocator.locate(position.getCurrent())
                .ifPresent(this::issueReport);
    }

    private void issueReport(TubeStation tubeStation) {
        Report report = new Report(id, LocalDateTime.now(), DronePosition.SPEED, assessTrafficCondition());
        logger.report(this, tubeStation, report);
    }

    private TrafficCondition assessTrafficCondition() {
        Double random = Math.random();
        return random < 0.34 ? LIGHT : random < 0.67 ? MODERATE : HEAVY;
    }

    private static class FixedSizeQueue<T> {
        private final Integer capacity;
        private final LinkedList<T> queue = new LinkedList<>();

        FixedSizeQueue(Integer capacity) {
            this.capacity = capacity;
        }

        void add(T element) {
            if (queue.size() >= capacity) {
                throw new DroneQueueIsFullException(capacity);
            }
            queue.add(element);
        }

        Optional<T> poll() {
            if (queue.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(queue.poll());
        }

        boolean hasCapacity() {
            return queue.size() < capacity;
        }
    }

    @Override
    public String toString() {
        return "TrafficDrone{" +
                "id=" + id +
                ", position=" + position +
                '}';
    }
}

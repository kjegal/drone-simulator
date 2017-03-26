package kg.trafficdrones.queue;

import kg.trafficdrones.route.Route;

import java.util.Optional;

public class PositionBroker {

    private final SimpleRouter<RouteMessage, Route, Integer> simpleRouter;

    public PositionBroker() {
        this.simpleRouter = new SimpleRouter<>();
    }

    public void publish(Route route) {
        simpleRouter.publish(new RouteMessage(route));
    }

    public Optional<Route> consume(Integer droneId) {
        return simpleRouter.consume(new DroneIdFilter(droneId));
    }
}

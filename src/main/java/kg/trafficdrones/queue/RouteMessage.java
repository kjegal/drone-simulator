package kg.trafficdrones.queue;

import kg.trafficdrones.route.Route;

import java.util.Objects;

class RouteMessage implements Message<Route, Integer> {

    private final Route route;

    RouteMessage(Route route) {
        Objects.requireNonNull(route);
        this.route = route;
    }

    @Override
    public Integer getId() {
        return route.getDroneId();
    }

    @Override
    public Route getBody() {
        return route;
    }
}

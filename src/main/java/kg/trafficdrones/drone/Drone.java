package kg.trafficdrones.drone;

public interface Drone {
    Integer getId();

    void refreshRoutes();

    void move();
}

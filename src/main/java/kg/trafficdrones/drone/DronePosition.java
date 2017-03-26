package kg.trafficdrones.drone;

import kg.trafficdrones.route.Coordinate;

import java.math.BigDecimal;

class DronePosition {
    static final BigDecimal SPEED = new BigDecimal("0.00001");

    private final Coordinate current;
    private final Coordinate destination;

    private DronePosition(Coordinate current, Coordinate destination) {
        this.current = current;
        this.destination = destination;
    }

    static DronePosition from(Coordinate startPosition) {
        return new DronePosition(startPosition, startPosition);
    }

    Coordinate getCurrent() {
        return current;
    }

    DronePosition move() {
        return new DronePosition(calculateNewPosition(current, destination), destination);
    }

    private Coordinate calculateNewPosition(Coordinate from, Coordinate to) {
        BigDecimal latitudeDelta = distanceToMove(from.getLatitude(), to.getLatitude());
        BigDecimal longitudeDelta = distanceToMove(from.getLongitude(), to.getLongitude());
        return new Coordinate(
                from.getLatitude().add(latitudeDelta),
                from.getLongitude().add(longitudeDelta)
        );
    }

    private BigDecimal distanceToMove(BigDecimal from, BigDecimal to) {
        BigDecimal distanceLeft = from.subtract(to).abs();
        if (distanceLeft.signum() == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal distanceToMove = SPEED.min(distanceLeft);
        if (from.compareTo(to) > 0) {
            return distanceToMove.negate();
        } else {
            return distanceToMove;
        }
    }

    boolean hasArrived() {
        return current.getLatitude().compareTo(destination.getLatitude()) == 0 &&
                current.getLongitude().compareTo(destination.getLongitude()) == 0;
    }

    DronePosition changeDestination(Coordinate newDestination) {
        return new DronePosition(current, newDestination);
    }

    @Override
    public String toString() {
        return "DronePosition{" +
                "current=" + current +
                ", destination=" + destination +
                '}';
    }
}

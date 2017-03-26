package kg.trafficdrones.station;

import kg.trafficdrones.csv.Mapper;
import kg.trafficdrones.route.Coordinate;

import java.math.BigDecimal;
import java.util.Optional;

public class TubeStationLocator extends Locator<TubeStation> {

    private static final BigDecimal DEFAULT_KILOMETER_NEARBY_RADIUS_THRESHOLD = new BigDecimal("0.350");

    private final BigDecimal kilometerNearbyRadiusThreshold;

    public TubeStationLocator() {
        this(DEFAULT_KILOMETER_NEARBY_RADIUS_THRESHOLD);
    }

    public TubeStationLocator(BigDecimal kilometerNearbyRadiusThreshold) {
        super(new TubeStationMapper(), Tree.empty());
        this.kilometerNearbyRadiusThreshold = kilometerNearbyRadiusThreshold;
    }

    @Override
    public Optional<TubeStation> locate(Coordinate position) {
        return locateNearby(position);
    }

    private Optional<TubeStation> locateNearby(Coordinate position) {
        return Optional.of(findNearest(position))
                .filter(nearest -> nearest.isNearby(position, kilometerNearbyRadiusThreshold));
    }

    private TubeStation findNearest(Coordinate position) {
        return tree.nearest(new TubeStation("dummy", position));
    }
}

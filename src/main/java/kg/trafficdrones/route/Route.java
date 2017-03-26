package kg.trafficdrones.route;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Route implements Serializable {
    private final Integer droneId;
    private final Coordinate position;
    private final LocalDateTime time;

    private Route(Integer droneId, BigDecimal latitude, BigDecimal longitude, LocalDateTime time) {
        Objects.requireNonNull(droneId);
        Objects.requireNonNull(latitude);
        Objects.requireNonNull(longitude);
        Objects.requireNonNull(time);
        this.droneId = droneId;
        this.position = new Coordinate(latitude, longitude);
        this.time = time;
    }

    public Integer getDroneId() {
        return droneId;
    }

    public Coordinate getPosition() {
        return position;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public static class RouteBuilder {
        private Integer droneId;
        private BigDecimal latitude;
        private BigDecimal longitude;
        private LocalDateTime time;

        public RouteBuilder droneId(Integer value) {
            droneId = value;
            return this;
        }

        public RouteBuilder latitude(BigDecimal value) {
            latitude = value;
            return this;
        }

        public RouteBuilder longitude(BigDecimal value) {
            longitude = value;
            return this;
        }

        public RouteBuilder time(LocalDateTime value) {
            time = value;
            return this;
        }

        public Route build() {
            return new Route(droneId, latitude, longitude, time);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (!droneId.equals(route.droneId)) return false;
        if (!position.equals(route.position)) return false;
        return time.equals(route.time);
    }

    @Override
    public int hashCode() {
        int result = droneId.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + time.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Route{" +
                "droneId=" + droneId +
                ", position=" + position +
                ", time=" + time +
                '}';
    }
}

package kg.trafficdrones.drone;

import kg.trafficdrones.station.TrafficCondition;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Report {
    private final Integer droneId;
    private final LocalDateTime time;
    private final BigDecimal speed;
    private final TrafficCondition trafficCondition;

    Report(Integer droneId, LocalDateTime time, BigDecimal speed, TrafficCondition trafficCondition) {
        this.droneId = droneId;
        this.time = time;
        this.speed = speed;
        this.trafficCondition = trafficCondition;
    }

    @Override
    public String toString() {
        return "Report{" +
                "droneId=" + droneId +
                ", time=" + time +
                ", speed=" + speed +
                ", trafficCondition=" + trafficCondition +
                '}';
    }
}

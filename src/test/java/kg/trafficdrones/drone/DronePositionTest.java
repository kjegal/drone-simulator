package kg.trafficdrones.drone;

import kg.trafficdrones.route.Coordinate;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DronePositionTest {

    private static final Coordinate START_POSITION =
            new Coordinate(new BigDecimal("51.476105"), new BigDecimal("-0.100224"));

    private static final Coordinate DESTINATION_POSITION =
            new Coordinate(new BigDecimal("51.475901"), new BigDecimal("-0.100111"));

    @Test
    public void newlyCreatedDroneHasArrived() throws Exception {
        assertTrue(DronePosition.from(START_POSITION).hasArrived());
    }

    @Test
    public void changeDestination() {
        assertFalse(
                DronePosition.from(START_POSITION)
                        .changeDestination(DESTINATION_POSITION)
                        .hasArrived()
        );
    }

    @Test
    public void move() {
        DronePosition dronePosition = DronePosition.from(START_POSITION).changeDestination(DESTINATION_POSITION);

        int moves = 0;
        while (!dronePosition.hasArrived()) {
            dronePosition = dronePosition.move();
            moves++;
        }

        assertTrue(dronePosition.hasArrived());
        assertThat(dronePosition.getCurrent(), is(DESTINATION_POSITION));
        assertThat(moves, is(21));
    }

}
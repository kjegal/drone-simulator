package kg.trafficdrones.queue;

import kg.trafficdrones.route.Route;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PositionBrokerTest {

    private PositionBroker positionBroker;

    @Before
    public void setUp() throws Exception {
        positionBroker = new PositionBroker();
    }

    @Test
    public void publishAndConsumeSuccess() throws Exception {
        Route route = routeWithId(1);
        positionBroker.publish(route);
        Optional<Route> result = positionBroker.consume(1);
        assertTrue(result.isPresent());
        assertThat(result.get(), is(route));
    }

    @Test
    public void consumeFromEmptyQueue() throws Exception {
        assertFalse(positionBroker.consume(1).isPresent());
    }

    @Test
    public void publishAndConsumeWithDifferentDroneIds() throws Exception {
        positionBroker.publish(routeWithId(1));
        assertFalse(positionBroker.consume(2).isPresent());
    }

    @Test
    public void publishAndConsumeMultipleDroneIds() throws Exception {
        positionBroker.publish(routeWithId(1));
        positionBroker.publish(routeWithId(2));
        positionBroker.publish(routeWithId(3));
        positionBroker.publish(routeWithId(2));
        positionBroker.publish(routeWithId(3));
        positionBroker.publish(routeWithId(1));
        assertTrue(positionBroker.consume(2).isPresent());
        assertTrue(positionBroker.consume(2).isPresent());
        assertTrue(positionBroker.consume(1).isPresent());
        assertTrue(positionBroker.consume(1).isPresent());
        assertTrue(positionBroker.consume(3).isPresent());
        assertTrue(positionBroker.consume(3).isPresent());
    }

    private static Route routeWithId(Integer id) {
        return new Route.RouteBuilder()
                .droneId(id)
                .latitude(new BigDecimal(Math.random()))
                .longitude(new BigDecimal(Math.random()))
                .time(LocalDateTime.now())
                .build();
    }
}
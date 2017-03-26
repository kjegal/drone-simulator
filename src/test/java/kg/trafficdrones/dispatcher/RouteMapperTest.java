package kg.trafficdrones.dispatcher;

import kg.trafficdrones.route.Coordinate;
import kg.trafficdrones.route.Route;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RouteMapperTest {

    private RouteMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new RouteMapper();
    }

    @Test
    public void map() throws Exception {
        Route route = mapper.map(Arrays.asList("1", "1.0", "0.1", "2017-03-25 12:00:00"));
        assertThat(route.getDroneId(), is(1));
        assertThat(route.getPosition(), is(new Coordinate(new BigDecimal("1.0"), new BigDecimal("0.1"))));
        assertThat(route.getTime(), is(LocalDateTime.of(2017, 3, 25, 12, 0, 0)));
    }

}
package kg.trafficdrones.station;

import kg.trafficdrones.route.Coordinate;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TubeStationLocatorTest {

    private TubeStationLocator locator;

    private static final List<List<String>> LOCATIONS = Arrays.asList(
            Arrays.asList("Baker Street", "51.52313", "-0.156904"),
            Arrays.asList("Covent Garden", "51.51276", "-0.124507"),
            Arrays.asList("Devons Road (DLR)", "51.522258", "-0.018008"),
            Arrays.asList("Finsbury Park", "51.564635", "-0.105881"),
            Arrays.asList("Greenwich", "51.477548", "-0.014863"),
            Arrays.asList("Heathrow Terminal 5", "51.471302", "-0.487827"),
            Arrays.asList("Hyde Park Corner", "51.502309", "-0.15443"),
            Arrays.asList("London Bridge", "51.504674", "-0.086006"),
            Arrays.asList("Notting Hill Gate", "51.509392", "-0.195834"),
            Arrays.asList("Royal Albert (DLR)", "51.508494", "0.045177")
    );

    private static final Coordinate FINSBURY_PARK =
            new Coordinate(new BigDecimal("51.564635"), new BigDecimal("-0.105881"));

    private static final Coordinate LONDON_BRIDGE =
            new Coordinate(new BigDecimal("51.504674"), new BigDecimal("-0.086006"));

    private static final Coordinate UNKNOWN_LOCATION =
            new Coordinate(new BigDecimal("49.123456"), new BigDecimal("-0.234567"));


    @Before
    public void setUp() {
        locator = new TubeStationLocator();
        locator.load(LOCATIONS::stream);
    }

    @Test
    public void locateExistingStationWithExactCoordinates() {
        Optional<TubeStation> result = locator.locate(FINSBURY_PARK);
        assertTrue(result.isPresent());
        assertThat(result.get().getName(), is("Finsbury Park"));
        assertThat(result.get().getPosition(), is(FINSBURY_PARK));
    }

    @Test
    public void locateExistingNearbyStation() {
        Coordinate closeToLondonBridge = new Coordinate(new BigDecimal("51.506"), new BigDecimal("-0.086"));
        Optional<TubeStation> result = locator.locate(closeToLondonBridge);
        assertTrue(result.isPresent());
        assertThat(result.get().getName(), is("London Bridge"));
        assertThat(result.get().getPosition(), is(LONDON_BRIDGE));
    }

    @Test
    public void locateNoNearbyStations() {
        Optional<TubeStation> result = locator.locate(UNKNOWN_LOCATION);
        assertFalse(result.isPresent());
    }
}
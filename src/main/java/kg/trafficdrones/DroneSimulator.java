package kg.trafficdrones;

import kg.trafficdrones.csv.CsvReader;
import kg.trafficdrones.dispatcher.RouteDispatcherService;
import kg.trafficdrones.drone.DroneService;
import kg.trafficdrones.logger.EventLogger;
import kg.trafficdrones.queue.PositionBroker;
import kg.trafficdrones.station.TubeStationLocator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class DroneSimulator {

    private final static LocalTime SHUTDOWN_TIME = LocalTime.of(8, 10);

    private final static List<Integer> DRONE_IDS = Arrays.asList(5937, 6043);

    private final EventLogger eventLogger = new EventLogger();

    public void start() {
        eventLogger.start();

        PositionBroker positionBroker = new PositionBroker();
        DroneService droneService = new DroneService(positionBroker, tubeStationLocator());

        RouteDispatcherService routeDispatcherService = new RouteDispatcherService(positionBroker);
        DRONE_IDS.stream()
                .map(droneService::startDrone)
                .forEach(routeDispatcherService::register);
        routeDispatcherService.start(streamOfDroneRoutes());

        routeDispatcherService.scheduleShutdown(minutesFromNow(SHUTDOWN_TIME), TimeUnit.MINUTES);
        eventLogger.finish();
    }

    private TubeStationLocator tubeStationLocator() {
        CsvReader stationsReader = new CsvReader("tube.csv");
        TubeStationLocator tubeStationLocator = new TubeStationLocator();
        tubeStationLocator.load(stationsReader::read);
        return tubeStationLocator;
    }

    private Stream<List<String>> streamOfDroneRoutes() {
        CsvReader drone1Reader = new CsvReader("5937.csv");
        CsvReader drone2Reader = new CsvReader("6043.csv");
        return Stream.concat(drone1Reader.read(), drone2Reader.read());
    }

    private long minutesFromNow(LocalTime futureTime) {
        Duration duration = durationFromNow(futureTime.atDate(LocalDate.now()));
        if (duration.isNegative()) {
            duration = durationFromNow(futureTime.atDate(LocalDate.now().plusDays(1)));
        }
        return duration.toMinutes();
    }

    private Duration durationFromNow(LocalDateTime other) {
        return Duration.between(LocalDateTime.now(), other);
    }
}

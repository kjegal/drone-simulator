package kg.trafficdrones.dispatcher;

import kg.trafficdrones.csv.Mapper;
import kg.trafficdrones.route.Route;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

class RouteMapper implements Mapper<Route> {

    private static DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Route map(List<String> fields) {
        Integer droneId = Integer.parseInt(fields.get(0));
        BigDecimal latitude = new BigDecimal(fields.get(1));
        BigDecimal longitude = new BigDecimal(fields.get(2));
        LocalDateTime time = LocalDateTime.parse(fields.get(3), DATE_TIME_FORMAT);
        return new Route.RouteBuilder()
                .droneId(droneId)
                .latitude(latitude)
                .longitude(longitude)
                .time(time)
                .build();
    }
}

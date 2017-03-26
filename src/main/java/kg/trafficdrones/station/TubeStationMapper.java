package kg.trafficdrones.station;

import kg.trafficdrones.csv.Mapper;
import kg.trafficdrones.route.Coordinate;

import java.math.BigDecimal;
import java.util.List;

class TubeStationMapper implements Mapper<TubeStation> {

    @Override
    public TubeStation map(List<String> from) {
        String name = from.get(0);
        BigDecimal latitude = new BigDecimal(from.get(1));
        BigDecimal longitude = new BigDecimal(from.get(2));
        return new TubeStation(name, new Coordinate(latitude, longitude));
    }
}

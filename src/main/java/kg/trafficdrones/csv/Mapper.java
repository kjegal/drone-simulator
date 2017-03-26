package kg.trafficdrones.csv;

import java.util.List;

public interface Mapper<T> {

    T map(List<String> from);
}

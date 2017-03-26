package kg.trafficdrones.station;

import kg.trafficdrones.csv.Mapper;
import kg.trafficdrones.route.Coordinate;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class Locator<T extends Comparable<T>> {

    private final Mapper<T> mapper;

    protected Tree<T> tree;

    public Locator(Mapper<T> mapper, Tree<T> tree) {
        this.mapper = mapper;
        this.tree = tree;
    }

    public void load(Supplier<Stream<List<String>>> supplier) {
        supplier.get()
                .map(mapper::map)
                .forEach(value -> tree = tree.insert(value));
    }

    public abstract Optional<T> locate(Coordinate position);
}

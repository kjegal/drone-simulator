package kg.trafficdrones.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class CsvReader {

    private final Path path;

    private final Function<String, String> stripQuotes = value -> value != null ? value.replaceAll("\"", "") : null;

    private final Function<String[], List<String>> transform = values -> Arrays.stream(values)
            .map(stripQuotes)
            .collect(toList());

    public CsvReader(String path) {
        this.path = Paths.get(path);
    }

    public Stream<List<String>> read() {
        try {
            return Files.newBufferedReader(path)
                    .lines()
                    .map(line -> line.split(","))
                    .map(transform);
        } catch (IOException e) {
            throw new CsvReaderException("Error loading '" + path + "'", e);
        }
    }
}

package kg.trafficdrones.csv;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.*;

public class CsvReaderTest {

    private static final String TEST_CSV_PATH = "src/test/resources/test.csv";

    private static final List<String> LINE_1 = Arrays.asList("a","b","c","d");
    private static final List<String> LINE_2 = Arrays.asList("1","2","3","4");
    private static final List<String> LINE_3 = Arrays.asList("x1","y2","z3","u4");

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void readSuccessfully() throws Exception {
        CsvReader reader = new CsvReader(TEST_CSV_PATH);

        List<List<String>> result = reader.read().collect(toList());
        assertThat(result.size(), is(3));
        assertThat(result, hasItems(LINE_1, LINE_2, LINE_3));
    }

    @Test
    public void readFileDoesNotExist() throws Exception {
        expectedException.expect(CsvReaderException.class);
        expectedException.expectCause(isA(IOException.class));
        expectedException.expectMessage("Error loading 'unknown'");

        new CsvReader("unknown").read();
    }

}
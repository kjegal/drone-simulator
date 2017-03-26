package kg.trafficdrones.logger;

import static java.time.LocalTime.now;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

class ConsoleLogger {

    void log(String message) {
        System.out.println(now().format(ISO_LOCAL_TIME) + " " + message);
    }
}

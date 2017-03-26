package kg.trafficdrones.dispatcher;

import java.util.List;
import java.util.stream.Stream;

public class RunnableDispatcher implements Runnable {

    private final Dispatcher dispatcher;

    private final Stream<List<String>> events;

    RunnableDispatcher(Dispatcher dispatcher, Stream<List<String>> events) {
        this.dispatcher = dispatcher;
        this.events = events;
    }

    @Override
    public void run() {
        dispatcher.publish(() -> events);
    }
}

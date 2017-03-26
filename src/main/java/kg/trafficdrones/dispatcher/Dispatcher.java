package kg.trafficdrones.dispatcher;

import kg.trafficdrones.csv.Mapper;
import kg.trafficdrones.logger.EventLogger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

abstract class Dispatcher<T> {

    private final EventLogger eventLogger = new EventLogger();

    private final Mapper<T> mapper;

    private final Consumer<T> messageBroker;

    Dispatcher(Mapper<T> mapper, Consumer<T> messageBroker) {
        this.mapper = mapper;
        this.messageBroker = messageBroker;
    }

    void publish(Supplier<Stream<List<String>>> supplier) {
        supplier.get()
                .map(mapper::map)
                .forEach(messageBroker.andThen(message -> eventLogger.publish(message.toString())));
    }

    abstract void register(ExecutorService executorService);

    abstract void broadcastShutdown();
}

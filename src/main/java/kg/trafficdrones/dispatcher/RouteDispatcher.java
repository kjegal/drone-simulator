package kg.trafficdrones.dispatcher;

import kg.trafficdrones.logger.EventLogger;
import kg.trafficdrones.queue.PositionBroker;
import kg.trafficdrones.route.Route;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

class RouteDispatcher extends Dispatcher<Route> {

    private final EventLogger eventLogger = new EventLogger();

    private final Set<ExecutorService> listeners = new HashSet<>(2);

    RouteDispatcher(PositionBroker broker) {
        super(new RouteMapper(), broker::publish);
    }

    @Override
    void register(ExecutorService executorService) {
        listeners.add(executorService);
    }

    @Override
    void broadcastShutdown() {
        eventLogger.shutdownBroadcasted();
        listeners.forEach(this::shutdownListener);
    }

    private void shutdownListener(ExecutorService listener) {
        try {
            listener.shutdown();
            listener.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Shutdown was interrupted");
        } finally {
            listener.shutdownNow();
        }
    }
}

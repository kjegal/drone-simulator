package kg.trafficdrones.dispatcher;

import kg.trafficdrones.queue.PositionBroker;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class RouteDispatcherService {

    private final Dispatcher routeDispatcher;

    private final ExecutorService dispatcherExecutor;

    public RouteDispatcherService(PositionBroker positionBroker) {
        this.routeDispatcher = new RouteDispatcher(positionBroker);
        this.dispatcherExecutor = Executors.newSingleThreadExecutor();
    }

    public void register(ExecutorService executorService) {
        routeDispatcher.register(executorService);
    }

    public void start(Stream<List<String>> events) {
        dispatcherExecutor.submit(new RunnableDispatcher(routeDispatcher, events));
    }

    public void scheduleShutdown(long delay, TimeUnit timeUnit) {
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture scheduledFuture = scheduledExecutor.schedule(this::shutdown, delay, timeUnit);

        try {
            scheduledFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Scheduled shutdown was interrupted");
        } finally {
            scheduledExecutor.shutdown();
        }
    }

    private void shutdown() {
        routeDispatcher.broadcastShutdown();
        try {
            dispatcherExecutor.shutdown();
            dispatcherExecutor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Shutdown was interrupted");
        } finally {
            dispatcherExecutor.shutdownNow();
        }
    }
}

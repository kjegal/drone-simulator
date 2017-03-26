package kg.trafficdrones.queue;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class SimpleRouter<M extends Message<TYPE, ID>, TYPE, ID> implements MessageRouter<M, TYPE, ID> {

    private final ConcurrentHashMap<ID, ConsumerQueue<M, TYPE, ID>> consumerQueues = new ConcurrentHashMap<>();

    @Override
    public void publish(M message) {
        consumerQueues.computeIfAbsent(message.getId(), id -> new ConsumerQueue()).add(message);
    }

    @Override
    public Optional<TYPE> consume(MessageFilter<ID> filter) {
        return Optional.ofNullable(consumerQueues.get(filter.get()))
                .flatMap(consumerQueue -> consumerQueue.remove(filter));
    }
}

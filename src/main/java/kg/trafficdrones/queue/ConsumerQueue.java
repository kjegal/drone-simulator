package kg.trafficdrones.queue;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;

class ConsumerQueue<M extends Message<TYPE, ID>, TYPE, ID> implements Queue<M, TYPE, ID> {
    private static final int MAX_CAPACITY = 10000;

    private final LinkedBlockingQueue<TYPE> queue;

    ConsumerQueue() {
        queue = new LinkedBlockingQueue<>(MAX_CAPACITY);
    }

    @Override
    public void add(M message) {
        queue.add(message.getBody());
    }

    @Override
    public Optional<TYPE> remove(MessageFilter<ID> messageFilter) {
        try {
            return Optional.of(queue.take());
        } catch (InterruptedException e) {
            return Optional.empty();
        }
    }
}

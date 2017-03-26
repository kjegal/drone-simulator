package kg.trafficdrones.queue;

import java.util.Optional;

interface Queue<M extends Message<TYPE, ID>, TYPE, ID> {
    void add(M message);

    Optional<TYPE> remove(MessageFilter<ID> filter);
}

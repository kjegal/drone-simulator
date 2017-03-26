package kg.trafficdrones.queue;

import java.util.Optional;

interface MessageRouter<M extends Message<TYPE, ID>, TYPE, ID> {

    void publish(M message);

    Optional<TYPE> consume(MessageFilter<ID> filter);
}

package kg.trafficdrones.queue;

import java.util.Optional;

class MessageBroker<M extends Message<TYPE, ID>, TYPE, ID> implements Queue<M, TYPE, ID> {

    private final MessageRouter<M, TYPE, ID> messageRouter;

    MessageBroker(MessageRouter<M, TYPE, ID> messageRouter) {
        this.messageRouter = messageRouter;
    }

    @Override
    public void add(M message) {
        messageRouter.publish(message);
    }

    @Override
    public Optional<TYPE> remove(MessageFilter<ID> filter) {
        return messageRouter.consume(filter);
    }
}

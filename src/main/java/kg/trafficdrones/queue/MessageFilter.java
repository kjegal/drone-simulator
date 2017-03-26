package kg.trafficdrones.queue;

interface MessageFilter<T> {
    T get();
}

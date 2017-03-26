package kg.trafficdrones.queue;

interface Message<TYPE, ID> {

    ID getId();

    TYPE getBody();
}

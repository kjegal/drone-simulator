package kg.trafficdrones.queue;

class DroneIdFilter implements MessageFilter<Integer> {

    private final Integer droneId;

    DroneIdFilter(Integer droneId) {
        this.droneId = droneId;
    }

    @Override
    public Integer get() {
        return droneId;
    }
}

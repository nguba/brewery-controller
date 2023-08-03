package application.port.output;

public interface EventBusOutputPort {

    void publish(Object event);

    void register(Object listener);

    void remove(Object listener);
}

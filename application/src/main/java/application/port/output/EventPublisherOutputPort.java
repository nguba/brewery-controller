package application.port.output;

public interface EventPublisherOutputPort {

    void publish(Object event);
}

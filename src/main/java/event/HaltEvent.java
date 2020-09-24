package event;

public class HaltEvent extends Event{
    @Override
    public EventType getEventType() {
        return EventType.HALT;
    }
}

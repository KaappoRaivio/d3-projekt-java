package event;

import entity.Entity;

public class KillEvent extends Event {
    private final Entity entity;

    public KillEvent (Entity entity) {
        this.entity = entity;
    }

    @Override
    public EventType getEventType() {
        return EventType.KILL;
    }

    public Entity getKilledEntity () {
        return entity;
    }
}

package event;

import entity.Entity;

import java.util.List;

public class CollisionEvent extends Event{
    private final List<Entity> collidingEntities;

    public CollisionEvent(List<Entity> collidingEntities) {
        this.collidingEntities = collidingEntities;
    }

    @Override
    public EventType getEventType() {
        return EventType.COLLISION;
    }

    public List<Entity> getCollidingEntities () {
        return collidingEntities;
    }
}

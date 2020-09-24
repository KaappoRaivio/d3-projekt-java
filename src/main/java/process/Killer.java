package process;

import entity.Entity;
import event.Event;
import event.EventType;
import event.KillEvent;
import scene.Scene;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class Killer implements Process {
    private Set<Entity> toBeKilled = new HashSet<>();

    @Override
    public Set<Entity> update(Scene scene, Set<Entity> entities, double deltaTime, int frameCounter, Function<Event, Void> dispatchEvent) {
        toBeKilled.forEach(entities::remove);
        toBeKilled.clear();
        return entities;
    }

    @Override
    public void onEvent(Event event, Function<Event, Void> dispatchEvent) {
        if (event.getEventType() == EventType.KILL) {
            toBeKilled.add(((KillEvent) event).getKilledEntity());
        }
    }
}

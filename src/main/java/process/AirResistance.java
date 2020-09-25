package process;

import entity.Entity;
import event.Event;
import scene.Scene;

import java.util.Set;
import java.util.function.Function;

public class AirResistance implements Process {
    private static final double AIR_RESISTANCE = 0.002;

    @Override
    public Set<Entity> update(Scene scene, Set<Entity> entities, double deltaTime, int frameCounter, Function<Event, Void> dispatchEvent) {
        entities.forEach(entity -> {
            if (entity.isMovable()) {

                entity.setAcceleration(
                            entity.getVelocity().normalize().multiply(
                                    entity
                                        .getVelocity()
                                        .toPowerOf(2).length()
                            )
                        .multiply(-1)
                        .multiply(AIR_RESISTANCE)
                        .multiply(entity.getEntityProperties().getCoeffOfAirResistance()));
                }
//            entity.setAcceleration(Vector2D.of(0, 0));
        });
        return entities;
    }

    @Override
    public void onEvent(Event event, Function<Event, Void> dispatchEvent, Set<Entity> entities) {

    }
}

package process;

import entity.CollisionModel;
import entity.Entity;
import event.CollisionEvent;
import event.Event;
import event.EventType;
import misc.Vector2D;
import scene.Scene;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

enum CollisionType {
    HORIZONTAL, VERTICAL;
}

public class Collider implements Process {
    @Override
    public Set<Entity> update(Scene scene, Set<Entity> entities, double deltaTime, int frameCounter, Function<Event, Void> dispatchEvent) {
        return entities;
    }

    @Override
    public void onEvent(Event event, Function<Event, Void> dispatchEvent, Set<Entity> entities) {
        if (event.getEventType() == EventType.COLLISION) {
            List<Entity> collidingEntities = ((CollisionEvent) event).getCollidingEntities();

            Entity entity1 = collidingEntities.get(0);
            Entity entity2 = collidingEntities.get(1);

//            if (!entity1.isMovable()) {
//                getCollisionType(entity1, entity2) == CollisionType.HORIZONTAL
//                        ? entity2.setVelocity(Vector2D.of(entity2.getVelocity().getI(), ));
//                entity2.setVelocity(entity2.getVelocity().multiply(-1).multiply(entity2.getEntityProperties().getBounciness()));

//                return;
//            } else if (!entity2.isMovable()) {
//                entity1.setVelocity(entity1.getVelocity().multiply(-1).multiply(entity1.getEntityProperties().getBounciness()));
//                return;
//            }

            Vector2D u1 = entity1.getVelocity();
            Vector2D u2 = entity2.getVelocity();

            double m1 = entity1.getEntityProperties().getMass();
            double m2 = entity2.getEntityProperties().getMass();

            double sumOfMasses = m1 + m2;
            Vector2D v1 = u1.multiply((m1 - m2) / sumOfMasses).add(u2.multiply(2 / sumOfMasses * m2));
            Vector2D v2 = u1.multiply(2 / sumOfMasses * m1).add(u2.multiply((m2 - m1) / sumOfMasses));

            double e1 = entity1.getEntityProperties().getBounciness();
            double e2 = entity2.getEntityProperties().getBounciness();

            double product = e1 * e2;

            if (entity1.getEntityProperties().getCollisionModel() != CollisionModel.CIRCULAR || entity2.getEntityProperties().getCollisionModel() != CollisionModel.CIRCULAR) {
                if (getCollisionType(entity1, entity2) == CollisionType.HORIZONTAL) {
                    v1 = Vector2D.of(v1.getI(), u1.getJ());
                    v2 = Vector2D.of(v2.getI(), u2.getJ());
                } else {
                    v1 = Vector2D.of(u1.getI(), v1.getJ());
                    v2 = Vector2D.of(u2.getI(), v2.getJ());
                }
            }

            entity1.setVelocity(v1.multiply(e1));
            entity2.setVelocity(v2.multiply(e2));
        }
    }
    private static CollisionType getCollisionType (Entity entity1, Entity entity2) {
        double deltaX = getCollisionDistance(entity1.getCenterPoint().getI(), entity2.getCenterPoint().getI(), entity1.getSprite().getDimensions().getI(), entity2.getSprite().getDimensions().getI());
        double deltaY = getCollisionDistance(entity1.getCenterPoint().getJ(), entity2.getCenterPoint().getJ(), entity1.getSprite().getDimensions().getJ(), entity2.getSprite().getDimensions().getJ());

        return Math.abs(deltaX) < Math.abs(deltaY) ? CollisionType.HORIZONTAL : CollisionType.VERTICAL;
    }

    private static double getCollisionDistance (double s1, double s2, double d1, double d2) {
        return Math.abs(s1 - s2) - (d1 / 2 + d2 / 2);
    }
}

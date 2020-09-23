package process;

import entity.Entity;
import misc.GeoHash;
import misc.Vector2D;
import scene.Scene;

import java.util.*;
import java.util.stream.Collectors;

public class Collision implements Process {
    private GeoHash geoHash;

    public Collision (Vector2D maxSize) {
        this.geoHash = new GeoHash(maxSize, 16, -3);
    }

    @Override
    public List<Entity> update(Scene scene, List<Entity> entities, double deltaTime, int frameCounter) {
        Map<String, List<Entity>> tiles = new HashMap<>();

        int step = 100;
        entities.forEach(entity -> {
            Set<String> hashes = geoHash.getAllGeoHashes(entity);
            hashes.forEach(hash -> {
                if (tiles.containsKey(hash)) {
                    tiles.get(hash).add(entity);
                } else {
                    tiles.put(hash, new ArrayList<>(Collections.singletonList(entity)));
                }
            });
        });
//        System.out.println(tiles
        Set<List<Entity>> coarseCollisions = tiles
                .keySet()
                .stream()
                .filter(key -> tiles.get(key).size() > 1)
                .map(tiles::get)
                .collect(Collectors.toSet());
        System.out.println("\n\nCOLLISIONS: " + coarseCollisions);
        Set<List<Entity>> trueCollisions = trueCollisions(coarseCollisions);
        System.out.println("TRUE COLLISIONS: " + trueCollisions);

        trueCollisions.forEach(collision -> {
            Entity entity1 = collision.get(0);
            Entity entity2 = collision.get(1);

            if (!entity1.isMovable()) {
                entity2.setVelocity(entity2.getVelocity().multiply(-1));
                return;
            } else if (!entity2.isMovable()) {
                entity1.setVelocity(entity1.getVelocity().multiply(-1));
                return;
            }

            Vector2D u1 = entity1.getVelocity();
            Vector2D u2 = entity2.getVelocity();

            double m1 = entity1.getMass();
            double m2 = entity2.getMass();

            Vector2D v1 = u1.multiply((m1 - m2) / (m1 + m2)).add(u2.multiply(2 / (m1 + m2) * m2));
            Vector2D v2 = u1.multiply(2 / (m1 + m2) * m1).add(u2.multiply((m2 - m1) / (m1 + m2)));

            entity1.setVelocity(v1);
            entity2.setVelocity(v2);
        });

        return entities;
    }

    private Set<List<Entity>> trueCollisions (Set<List<Entity>> coarseCollisions) {
        return coarseCollisions
                .stream()
                .filter(collision -> {
                    Entity entity1 = collision.get(0);
                    Entity entity2 = collision.get(1);

                    double startX1 = entity1.getPosition().getI();
                    double endX1 = entity1.getPosition().add(entity1.getSprite().getDimensions()).getI();

                    double startX2 = entity2.getPosition().getI();
                    double endX2 = entity2.getPosition().add(entity2.getSprite().getDimensions()).getI();

                    boolean xCollision = startX1 < startX2 && endX1 > startX2
                                      || startX1 < endX2 && endX1 > startX2;

                    double startY1 = entity1.getPosition().getJ();
                    double endY1 = entity1.getPosition().add(entity1.getSprite().getDimensions()).getJ();

                    double startY2 = entity2.getPosition().getJ();
                    double endY2 = entity2.getPosition().add(entity2.getSprite().getDimensions()).getJ();

                    boolean yCollision = startY1 < startY2 && endY1 > startY2
                            || startY1 < endY2 && endY1 > startY2;

                    return xCollision && yCollision;
                })
                .collect(Collectors.toSet());
    }
}

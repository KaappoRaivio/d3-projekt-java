package process;

import entity.Entity;
import event.CollisionEvent;
import event.Event;
import misc.GeoHash;
import misc.Vector2D;
import scene.Scene;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Collision implements Process {
    private GeoHash geoHash;

    public int getAmountOfCollisions() {
        return amountOfCollisions;
    }

    private int amountOfCollisions = 0;

    public Collision (Vector2D maxSize) {
        this.geoHash = new GeoHash(maxSize, 16, -3);
    }

    @Override
    public List<Entity> update(Scene scene, List<Entity> entities, double deltaTime, int frameCounter, Function<Event, Void> dispatchEvent) {
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

        trueCollisions.forEach(collision -> dispatchEvent.apply(new CollisionEvent(collision)));
        
        return entities;
    }

    @Override
    public void onEvent(Event event) {

    }

    private List<List<Entity>> individualPairs(List<Entity> list) {
        Set<List<Entity>> pairs = new HashSet<>();

        for (Entity entity1 : list) {
            for (Entity entity2 : list) {
                if (!entity1.equals(entity2) && !pairs.contains(Arrays.asList(entity1, entity2)) && !pairs.contains(Arrays.asList(entity2, entity1))) {
                    pairs.add(Arrays.asList(entity1, entity2));
                }
            }
        }

        return new ArrayList<>(pairs);
    }

    private Set<List<Entity>> trueCollisions (Set<List<Entity>> coarseCollisions) {
        Set<List<Entity>> collection = coarseCollisions
                .stream()
                .flatMap(list -> individualPairs(list).stream())
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

        amountOfCollisions += collection.size();

        return collection;
    }
}

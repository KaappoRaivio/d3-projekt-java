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
        System.out.println("TRUE COLLISIONS: " + trueCollisions(coarseCollisions));
//                .map(key -> key + ": " + tiles.get(key))
//                .collect(Collectors.joining("\n")));
//        System.out.println(tiles
//                .keySet()
//                .stream()
//                .map(key -> tiles.get(key).size())
//                .map(String::valueOf)
//                .collect(Collectors.joining(", ")));

        return entities;
    }

    private Set<Entity> trueCollisions (Set<List<Entity>> coarseCollisions) {
        return coarseCollisions
                .stream()
                .filter(collision -> {
                    double x1 = collision.get(0).getPosition().getI();
                    double x2 = collision.get(1).getPosition().getI();
                })
                .collect(Collectors.toSet());
    }
}

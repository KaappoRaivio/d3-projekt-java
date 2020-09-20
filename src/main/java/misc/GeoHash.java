package misc;

import entity.Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GeoHash {
    private Vector2D size;
    private int amountOfCells;
    private int depth;

    public GeoHash(Vector2D size, int amountOfCells, int depth) {
        int necessarySize = getNextPowerOfTwo((int) size.max());

        this.size = Vector2D.of(necessarySize, necessarySize);
        this.amountOfCells = amountOfCells;
        if (depth > 0) {
            this.depth = depth;
        } else {
            this.depth = (int)(Math.log(necessarySize) / Math.log(Math.sqrt(amountOfCells))) + depth;
            System.out.println("Depth: " + this.depth);
        }
    }

    private static int getNextPowerOfTwo (int number) {
        return (int) Math.pow(2, Math.ceil(Math.log(number) / Math.log(2)));
    }

    public String getGeoHash (Vector2D position) {
        StringBuilder hash = new StringBuilder();

        for (int level = 0; level < depth; ++level) {
            int cellSideLength = getCellSideLength(level);
//            System.out.println("level: " + level + ", " + cellSideLength);

            int xIndex = position.getIInt() / cellSideLength;
            int yIndex = position.getJInt() / cellSideLength;

            hash.append(getCharacterFromIndices(xIndex, yIndex));

            position = position.add(Vector2D.of(-xIndex * cellSideLength, -yIndex * cellSideLength));
//            System.out.println(cellSideLength);

        }

        return hash.toString();
    }

    private int getCellSideLength(int level) {
        return this.size.getIInt() / (int) Math.pow((int) Math.sqrt(this.amountOfCells), level + 1);
    }

    public Set<String> getAllGeoHashes (Entity entity) {
         Vector2D upperLeft = entity.getPosition();
         Vector2D lowerRight = entity.getPosition().add(entity.getSprite().getDimensions());
         Vector2D lowerLeft = Vector2D.of(upperLeft.getI(), lowerRight.getJ());
         Vector2D upperRight = Vector2D.of(lowerRight.getI(), upperLeft.getJ());

//        System.out.println(upperLeft + ", " + upperRight);
//        System.out.println(lowerLeft + ", " + lowerRight);

        Set<String> hashes = new HashSet<>();

        int cellSideLength = getCellSideLength(depth);
        for (int y = upperLeft.getJInt(); y < lowerLeft.getJInt(); y += cellSideLength) {
             for (int x = upperLeft.getIInt(); x < upperRight.getIInt(); x += cellSideLength) {
                 hashes.add(getGeoHash(Vector2D.of(x, y)));
             }
        }

        return hashes;
    }

    private String getCharacterFromIndices (int xIndex, int yIndex) {
        return "" + (char) (xIndex + 65) + (yIndex) + " ";
    }

    public static void main(String[] args) {
//        System.out.println(GeoHash.getNextPowerOfTwo(2));
        GeoHash geoHash = new GeoHash(Vector2D.of(640, 480), 4, 9);
        System.out.println(geoHash.getGeoHash(Vector2D.of(639, 479)));
        System.out.println(geoHash.size);
    }
}

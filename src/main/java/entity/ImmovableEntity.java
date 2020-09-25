package entity;

import misc.NonZero;
import misc.Pixel;
import misc.Vector2D;
import sprite.Sprite;

import java.util.Random;

public class ImmovableEntity extends MovableEntity {

    public ImmovableEntity(Vector2D position, String name, Sprite sprite) {
        super(position, name, sprite);
    }

    public ImmovableEntity (Vector2D position, String name, Sprite sprite, EntityProperties entityProperties) {
        super(position, name, sprite, entityProperties);
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public void setPosition(Vector2D newPosition) {
//        throw new RuntimeException("Cannot set position of an Immovable entity!");
    }

    @Override
    public void setVelocity(Vector2D newVelocity) {
//        throw new RuntimeException("Cannot set velocity of an Immovable entity!");

    }

    @Override
    public void setAcceleration(Vector2D newAcceleration) {
//        throw new RuntimeException("Cannot set acceleration of an Immovable entity!");
    }
}
//public class ImmovableEntity extends Entity {
//    private final Vector2D position;
//    private final String name;
//    private final Sprite sprite;
//    private final int id;
//
//    public ImmovableEntity(Vector2D position, String name, Sprite sprite) {
//        this.position = position;
//        this.name = name;
//        this.sprite = sprite;
//        this.id = new Random().nextInt();
//    }
//
//    @Override
//    public Vector2D getPosition() {
//        return position;
//    }
//
//    @Override
//    public void setPosition(Vector2D newPosition) {
//        System.err.println("No way");
//    }
//
//    @Override
//    public Vector2D getVelocity() {
//        return Vector2D.of(0, 0);
//    }
//
//    @Override
//    public void setVelocity(Vector2D newVelocity) {
//
//    }
//
//    @Override
//    public Vector2D getAcceleration() {
//        return Vector2D.of(0, 0);
//    }
//
//    @Override
//    public void setAcceleration(Vector2D newAcceleration) {
//
//    }
//
//    @Override
//    public boolean collisionsEnabled() {
//        return true;
//    }
//
//    @Override
//    public Vector2D getCenterPoint() {
//        return position.add(sprite.getDimensions().divide(2));
//    }
//
//    @Override
//    public @NonZero double getMass() {
//        return 1;
//    }
//
//    @Override
//    public boolean isMovable() {
//        return false;
//    }
//
//    @Override
//    public Sprite getSprite() {
//        return sprite;
//    }
//
//
//    @Override
//    public String getName() {
//        return name;
//    }
//
//    @Override
//    public int getId() {
//        return id;
//    }
//}

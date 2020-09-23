package entity;

import misc.NonZero;
import misc.Pixel;
import misc.Vector2D;
import sprite.Sprite;

import java.util.Objects;
import java.util.Random;

public class MovableEntity extends Entity {
    private static final Random RANDOM = new Random();
    private Sprite sprite;
    private String name;
    private int id;
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private final double mass;


    public MovableEntity(Vector2D position, String name, Sprite sprite) {
        this(position, name, sprite, 1);
    }

    public MovableEntity(Vector2D position, String name, Sprite sprite, double mass) {
        this(position, Vector2D.of(0, 0), Vector2D.of(0, 0), name, sprite, mass);
    }

    public MovableEntity(Vector2D position, Vector2D velocity, String name, Sprite sprite, double mass) {
        this(position, velocity, Vector2D.of(0, 0), name, sprite, mass);
    }

    public MovableEntity(Vector2D position, Vector2D velocity, Vector2D acceleration, String name, Sprite sprite, double mass) {
        this.position = position;
        this.name = name;
        this.sprite = sprite;

        this.velocity = velocity;
        this.acceleration = acceleration;
        this.id = RANDOM.nextInt();
        this.mass = mass;
    }

    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector2D newPosition) {
        position = newPosition;
//        System.out.println("Warning: setting position directly, entity " + this);
    }

    @Override
    public Vector2D getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vector2D newVelocity) {
        velocity = newVelocity;
    }

    @Override
    public Vector2D getAcceleration() {
        return acceleration;
    }

    @Override
    public void setAcceleration(Vector2D newAcceleration) {
        acceleration = newAcceleration;
    }

    @Override
    public boolean collisionsEnabled() {
        return true;
    }

    @Override
    public Vector2D getCenterPoint() {
        return position.add(sprite.getDimensions().divide(2));
    }

    @Override
    public @NonZero double getMass() {
        return mass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovableEntity that = (MovableEntity) o;
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

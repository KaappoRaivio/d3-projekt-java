package entity;

import misc.Pixel;
import misc.Vector2D;
import sprite.Sprite;

public class MovableEntity extends Entity {
    private Sprite sprite;
    private String name;
    private int id;
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;

    public MovableEntity(Vector2D position, String name, Pixel[][] assetMatrix) {
        this(position, name, assetMatrix, Vector2D.of(1, 1));
    }

    public MovableEntity(Vector2D position, String name, Pixel[][] assetMatrix, Vector2D scale) {
        this(position, Vector2D.of(0, 0), Vector2D.of(0, 0), name, assetMatrix, scale);
    }

    public MovableEntity(Vector2D position, Vector2D velocity, String name, Pixel[][] assetMatrix, Vector2D scale) {
        this(position, velocity, Vector2D.of(0, 0), name, assetMatrix, scale);
    }

    public MovableEntity(Vector2D position, Vector2D velocity, Vector2D acceleration, String name, Pixel[][] assetMatrix, Vector2D scale) {
        this.position = position;
        this.name = name;
        this.sprite = new Sprite(assetMatrix, scale);

        this.velocity = velocity;
        this.acceleration = acceleration;
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
}
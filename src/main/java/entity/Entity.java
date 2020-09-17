package entity;

import misc.Vector2D;
import sprite.Sprite;

public abstract class Entity {
    public abstract boolean isMovable();
    public abstract Sprite getSprite();
    public abstract String getName();
    public abstract int getId();
    public abstract Vector2D getPosition();
    public abstract void setPosition(Vector2D newPosition);
    public abstract Vector2D getVelocity();
    public abstract void setVelocity(Vector2D newVelocity);
    public abstract Vector2D getAcceleration();
    public abstract void setAcceleration(Vector2D newAcceleration);

    @Override
    public String toString () {
        return getClass().toString() + " at " + getPosition() + " with id " + getId() + " and name " + getName();
    }
}

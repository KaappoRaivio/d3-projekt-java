package entity;

import misc.NonZero;

public class EntityProperties {
    private final double elasticity;
    private final double mass;
    private final double coeffOfAirResistance;
    private final CollisionModel collisionModel;

    public EntityProperties(double elasticity, double mass) {
        this(elasticity, mass, 1, CollisionModel.CIRCULAR);
    }

    public EntityProperties(double elasticity, double mass, double coeffOfAirResistance, CollisionModel collisionModel) {
        this.elasticity = elasticity;
        this.mass = mass;
        this.coeffOfAirResistance = coeffOfAirResistance;
        this.collisionModel = collisionModel;
    }

    public double getElasticity() {
        return elasticity;
    }

    public double getBounciness () {
        return getElasticity();
    }

    public double getMass() {
        return mass;
    }

    public double getCoeffOfAirResistance() {
        return coeffOfAirResistance;
    }

    public CollisionModel getCollisionModel() {
        return collisionModel;
    }
}

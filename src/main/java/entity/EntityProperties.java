package entity;

import misc.NonZero;

public class EntityProperties {
    private final double elasticity;
    private final double mass;

    public EntityProperties(double elasticity, double mass) {
        this.elasticity = elasticity;
        this.mass = mass;
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
}

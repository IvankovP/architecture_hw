package space.battle.core.adapter;

import space.battle.core.entity.UObject;
import space.battle.core.exception.CommandException;
import space.battle.core.movement.VelocityChangeable;
import space.battle.core.support.Direction;
import space.battle.core.support.Vector;

public class VelocityAdapter implements VelocityChangeable {

    private final UObject velocityObject;

    public VelocityAdapter(UObject velocityObject) {
        this.velocityObject = velocityObject;
    }

    @Override
    public void setVelocity(Vector newVelocity) {
        if (velocityObject == null) {
            throw new CommandException();
        }
        velocityObject.setProperty("velocity", newVelocity);
    }

    @Override
    public Vector calculateVelocity() {
        Vector velocity = (Vector) getProperty("velocity");
        if (!isRotable(velocityObject)) {
            return velocity;
        }

        Direction direction = (Direction) getProperty("direction");
        int directionSections = Math.max(0, (int) getProperty("directionSections"));

        int alpha = 360/directionSections;
        int currentAlpha = Math.abs(direction.getDirection() * alpha);
        double currentVelocity = Math.sqrt(Math.pow(velocity.getX(), 2) + Math.pow(velocity.getY(), 2));

        int x = (int) Math.round(currentVelocity * Math.cos(currentAlpha));
        int y = (int) Math.round(currentVelocity * Math.sin(currentAlpha));

        return new Vector(x, y);
    }

    private boolean isRotable(UObject rotableObject) {
        return rotableObject != null && Boolean.TRUE.equals(rotableObject.getProperty("rotable"));
    }

    private Object getProperty(String name) {
        Object property = velocityObject.getProperty(name);
        if (property != null) {
            return property;
        }
        throw new CommandException();
    }
}

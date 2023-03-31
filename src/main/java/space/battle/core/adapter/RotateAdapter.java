package space.battle.core.adapter;

import space.battle.core.entity.UObject;
import space.battle.core.movement.Rotable;
import space.battle.core.support.Direction;

public class RotateAdapter implements Rotable {

    private final UObject rotableObject;

    public RotateAdapter(UObject rotableObject) {
        this.rotableObject = rotableObject;
    }

    @Override
    public Direction getDirection() {
        return (Direction) getProperty("direction");
    }

    @Override
    public void setDirection(Direction newDirection) {
        if (!isRotable(rotableObject)) {
            throw new UnsupportedOperationException();
        }
        rotableObject.setProperty("direction", newDirection);
    }

    private boolean isRotable(UObject rotableObject) {
        return rotableObject != null && Boolean.TRUE.equals(rotableObject.getProperty("rotable"));
    }

    @Override
    public int getAngularVelocity() {
        return (int) getProperty("angularVelocity");
    }

    @Override
    public int getDirectionSections() {
        return (int) getProperty("directionSections");
    }

    private Object getProperty(String name) {
        Object property = rotableObject.getProperty(name);
        if (property != null) {
            return property;
        }
        throw new UnsupportedOperationException();
    }
}

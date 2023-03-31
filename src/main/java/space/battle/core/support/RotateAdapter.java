package space.battle.core.support;

import space.battle.core.entity.UObject;
import space.battle.core.movement.Rotable;

public class RotateAdapter implements Rotable {

    private final UObject rotableObject;

    public RotateAdapter(UObject rotableObject) {
        this.rotableObject = rotableObject;
    }

    @Override
    public Direction getDirection() {
        return (Direction) rotableObject.getProperty("direction");
    }

    @Override
    public void setDirection(Direction newDirection) {
        rotableObject.setProperty("direction", newDirection);
    }

    @Override
    public int getAngularVelocity() {
        return (int) rotableObject.getProperty("angularVelocity");
    }

    @Override
    public int getDirectionSections() {
        return (int) rotableObject.getProperty("directionSections");
    }
}

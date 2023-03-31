package space.battle.core.movement;

import space.battle.core.support.Direction;

public class Rotate {
    private final Rotable rotableObject;

    public Rotate(Rotable rotableObject) {
        this.rotableObject = rotableObject;
    }

    public void execute() {
        Direction direction = rotableObject.getDirection();
        int angularVelocity = rotableObject.getAngularVelocity();
        int directionSections = rotableObject.getDirectionSections();

        rotableObject.setDirection(direction.next(angularVelocity, directionSections));
    }
}

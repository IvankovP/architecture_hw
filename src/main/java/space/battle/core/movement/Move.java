package space.battle.core.movement;

import space.battle.core.support.Vector;

public class Move {
    private final Movable movableObject;

    public Move(Movable movableObject) {
        this.movableObject = movableObject;
    }

    public void execute() {
        Vector newPosition = Vector.plus(movableObject.getPosition(), movableObject.getVelocity());
        movableObject.setPosition(newPosition);
    }
}

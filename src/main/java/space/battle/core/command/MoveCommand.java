package space.battle.core.command;

import space.battle.core.movement.Movable;
import space.battle.core.support.Vector;

public class MoveCommand {
    private final Movable movableObject;

    public MoveCommand(Movable movableObject) {
        this.movableObject = movableObject;
    }

    public void execute() {
        Vector newPosition = Vector.plus(movableObject.getPosition(), movableObject.getVelocity());
        movableObject.setPosition(newPosition);
    }
}

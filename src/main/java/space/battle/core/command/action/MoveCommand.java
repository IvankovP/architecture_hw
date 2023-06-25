package space.battle.core.command.action;

import space.battle.core.command.Command;
import space.battle.core.movement.Movable;
import space.battle.core.support.Vector;

public class MoveCommand implements Command {

    private final Movable movableObject;

    public MoveCommand(Movable movableObject) {
        this.movableObject = movableObject;
    }

    @Override
    public void execute() {
        Vector newPosition = Vector.plus(movableObject.getPosition(), movableObject.getVelocity());
        movableObject.setPosition(newPosition);
    }
}

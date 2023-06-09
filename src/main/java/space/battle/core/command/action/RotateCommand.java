package space.battle.core.command.action;

import space.battle.core.command.Command;
import space.battle.core.movement.Rotable;
import space.battle.core.support.Direction;

public class RotateCommand implements Command {

    private final Rotable rotableObject;

    public RotateCommand(Rotable rotableObject) {
        this.rotableObject = rotableObject;
    }

    @Override
    public void execute() {
        Direction direction = rotableObject.getDirection();
        int angularVelocity = rotableObject.getAngularVelocity();
        int directionSections = rotableObject.getDirectionSections();

        rotableObject.setDirection(direction.next(angularVelocity, directionSections));
    }
}

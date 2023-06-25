package space.battle.core.command;

import space.battle.core.movement.VelocityChangeable;

public class ChangeVelocityCommand implements Command {

    private final VelocityChangeable velocityObject;

    public ChangeVelocityCommand(VelocityChangeable velocityObject) {
        this.velocityObject = velocityObject;
    }

    @Override
    public void execute() {
        velocityObject.setVelocity(velocityObject.calculateVelocity());
    }
}

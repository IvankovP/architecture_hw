package space.battle.core.command.action;

import space.battle.core.command.Command;
import space.battle.core.movement.Fuelable;

public class CheckFuelCommand implements Command {

    private final Fuelable fuelableObject;

    public CheckFuelCommand(Fuelable fuelableObject) {
        this.fuelableObject = fuelableObject;
    }

    @Override
    public void execute() {
        fuelableObject.checkFuel();
    }
}

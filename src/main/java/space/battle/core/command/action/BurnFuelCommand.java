package space.battle.core.command.action;

import space.battle.core.command.Command;
import space.battle.core.movement.Fuelable;

public class BurnFuelCommand implements Command {

    private final Fuelable burnFuelableObject;

    public BurnFuelCommand(Fuelable burnFuelableObject) {
        this.burnFuelableObject = burnFuelableObject;
    }

    @Override
    public void execute() {
        burnFuelableObject.burnFuel();
    }
}

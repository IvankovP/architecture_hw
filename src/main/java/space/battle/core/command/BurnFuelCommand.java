package space.battle.core.command;

import space.battle.core.movement.Fuelable;

public class BurnFuelCommand implements Command {

    private Fuelable burnFuelableObject;

    public BurnFuelCommand(Fuelable burnFuelableObject) {
        this.burnFuelableObject = burnFuelableObject;
    }

    @Override
    public void execute() {
        burnFuelableObject.burnFuel();
    }
}

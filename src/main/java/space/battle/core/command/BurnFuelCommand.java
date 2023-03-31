package space.battle.core.command;

import space.battle.core.movement.Fuelable;

public class BurnFuelCommand {

    private Fuelable burnFuelableObject;

    public BurnFuelCommand(Fuelable burnFuelableObject) {
        this.burnFuelableObject = burnFuelableObject;
    }

    public void execute() {
        burnFuelableObject.burnFuel();
    }
}

package space.battle.core.command;

import space.battle.core.movement.Fuelable;

public class CheckFuelCommand {

    private Fuelable fuelableObject;

    public CheckFuelCommand(Fuelable fuelableObject) {
        this.fuelableObject = fuelableObject;
    }

    public void execute() {
        fuelableObject.getFuel();
    }
}

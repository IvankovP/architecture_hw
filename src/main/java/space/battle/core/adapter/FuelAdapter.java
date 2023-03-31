package space.battle.core.adapter;

import space.battle.core.entity.UObject;
import space.battle.core.exception.CommandException;
import space.battle.core.movement.Fuelable;

public class FuelAdapter implements Fuelable {

    private UObject fuelableObject;

    public FuelAdapter(UObject fuelableObject) {
        this.fuelableObject = fuelableObject;
    }

    @Override
    public int getFuel() {
        return (int) getProperty("fuel");
    }

    @Override
    public void burnFuel() {
        int fuel = getFuel();
        int burnFuelCount = (int) getProperty("burnFuelCount");
        fuelableObject.setProperty("fuel", Math.max(0, fuel - burnFuelCount));
    }

    private Object getProperty(String name) {
        Object property = fuelableObject.getProperty(name);
        if (property != null) {
            return property;
        }
        throw new CommandException();
    }
}

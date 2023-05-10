package space.battle.core.adapter;

import space.battle.core.entity.UObject;
import space.battle.core.exception.CommandException;
import space.battle.core.movement.Fuelable;

public class FuelAdapter implements Fuelable {

    private final UObject fuelableObject;

    public FuelAdapter(UObject fuelableObject) {
        this.fuelableObject = fuelableObject;
    }

    @Override
    public void checkFuel() {
        int burnFuelCount = (int) getProperty("burnFuelCount");
        int fuel = getFuel();
        if (burnFuelCount <= 0 || fuel - burnFuelCount < 0) {
            throw new CommandException("Error check fuel");
        }
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
        System.out.println(fuelableObject.getProperty("fuel"));
    }

    private Object getProperty(String name) {
        Object property = fuelableObject.getProperty(name);
        if (property != null) {
            return property;
        }
        throw new CommandException("Error get parameter " + name);
    }
}

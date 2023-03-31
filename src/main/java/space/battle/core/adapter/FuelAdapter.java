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

    private Object getProperty(String name) {
        Object property = fuelableObject.getProperty(name);
        if (property != null) {
            return property;
        }
        throw new CommandException();
    }
}

package space.battle.core;

import org.junit.jupiter.api.Test;
import space.battle.core.adapter.FuelAdapter;
import space.battle.core.command.BurnFuelCommand;
import space.battle.core.entity.Ship;
import space.battle.core.entity.UObject;
import space.battle.core.exception.CommandException;
import space.battle.core.support.Vector;

import static org.junit.jupiter.api.Assertions.*;

class BurnFuelTest {

    @Test
    void checkFuelTest() {
        UObject ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("position", new Vector(12, 5));
        ship.setProperty("velocity", new Vector(-7, 3));
        ship.setProperty("fuel", 100);
        ship.setProperty("burnFuelCount", 5);

        FuelAdapter fuelAdapter = new FuelAdapter(ship);
        BurnFuelCommand burnFuelCommand = new BurnFuelCommand(fuelAdapter);
        burnFuelCommand.execute();

        assertEquals(95, fuelAdapter.getFuel());
    }

    @Test
    void errorGettingBurnFuelCountTest() {
        UObject ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("position", new Vector(12, 5));
        ship.setProperty("velocity", new Vector(-7, 3));
        ship.setProperty("fuel", 100);

        FuelAdapter fuelAdapter = new FuelAdapter(ship);
        BurnFuelCommand burnFuelCommand = new BurnFuelCommand(fuelAdapter);

        assertThrows(CommandException.class, burnFuelCommand::execute);
    }
}

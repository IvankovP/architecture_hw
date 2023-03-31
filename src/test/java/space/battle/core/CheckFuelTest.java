package space.battle.core;

import org.junit.jupiter.api.Test;
import space.battle.core.adapter.FuelAdapter;
import space.battle.core.adapter.MoveAdapter;
import space.battle.core.command.CheckFuelCommand;
import space.battle.core.command.MoveCommand;
import space.battle.core.entity.Planet;
import space.battle.core.entity.Ship;
import space.battle.core.entity.UObject;
import space.battle.core.exception.CommandException;
import space.battle.core.support.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckFuelTest {

    @Test
    void checkFuelTest() {
        UObject ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("position", new Vector(12, 5));
        ship.setProperty("velocity", new Vector(-7, 3));
        ship.setProperty("fuel", 100);

        FuelAdapter fuelAdapter = new FuelAdapter(ship);
        CheckFuelCommand checkFuelCommand = new CheckFuelCommand(fuelAdapter);
        checkFuelCommand.execute();

        assertEquals(100, fuelAdapter.getFuel());
    }

    @Test
    void errorCheckFuelTest() {
        UObject ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("position", new Vector(12, 5));
        ship.setProperty("velocity", new Vector(-7, 3));

        FuelAdapter fuelAdapter = new FuelAdapter(ship);
        CheckFuelCommand checkFuelCommand = new CheckFuelCommand(fuelAdapter);

        assertThrows(CommandException.class, checkFuelCommand::execute);
    }
}
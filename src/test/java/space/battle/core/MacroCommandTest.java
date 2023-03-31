package space.battle.core;

import org.junit.jupiter.api.Test;
import space.battle.core.adapter.FuelAdapter;
import space.battle.core.adapter.MoveAdapter;
import space.battle.core.command.BurnFuelCommand;
import space.battle.core.command.CheckFuelCommand;
import space.battle.core.command.MacroCommand;
import space.battle.core.command.MoveCommand;
import space.battle.core.entity.Ship;
import space.battle.core.entity.UObject;
import space.battle.core.exception.CommandException;
import space.battle.core.support.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MacroCommandTest {

    @Test
    void macroCommandTest() {
        UObject ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("position", new Vector(12, 5));
        ship.setProperty("velocity", new Vector(-7, 3));
        ship.setProperty("fuel", 100);
        ship.setProperty("burnFuelCount", 5);

        MoveAdapter moveAdapter = new MoveAdapter(ship);
        MoveCommand moveCommand = new MoveCommand(moveAdapter);

        FuelAdapter fuelAdapter = new FuelAdapter(ship);
        CheckFuelCommand checkFuelCommand = new CheckFuelCommand(fuelAdapter);
        BurnFuelCommand burnFuelCommand = new BurnFuelCommand(fuelAdapter);

        MacroCommand macroCommand = new MacroCommand(List.of(checkFuelCommand, moveCommand, burnFuelCommand));
        macroCommand.execute();

        assertEquals(new Vector(5, 8), moveAdapter.getPosition());
        assertEquals(95, fuelAdapter.getFuel());
    }

    @Test
    void errorMacroCommandTest() {
        UObject ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("position", new Vector(12, 5));
        ship.setProperty("velocity", new Vector(-7, 3));
        ship.setProperty("fuel", 100);

        MoveAdapter moveAdapter = new MoveAdapter(ship);
        MoveCommand moveCommand = new MoveCommand(moveAdapter);

        FuelAdapter fuelAdapter = new FuelAdapter(ship);
        CheckFuelCommand checkFuelCommand = new CheckFuelCommand(fuelAdapter);
        BurnFuelCommand burnFuelCommand = new BurnFuelCommand(fuelAdapter);

        MacroCommand macroCommand = new MacroCommand(List.of(checkFuelCommand, moveCommand, burnFuelCommand));

        assertThrows(CommandException.class, macroCommand::execute);
    }
}

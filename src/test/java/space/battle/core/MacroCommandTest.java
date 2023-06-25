package space.battle.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.battle.core.adapter.FuelAdapter;
import space.battle.core.adapter.MoveAdapter;
import space.battle.core.adapter.RotateAdapter;
import space.battle.core.adapter.VelocityAdapter;
import space.battle.core.command.*;
import space.battle.core.command.action.BurnFuelCommand;
import space.battle.core.command.action.CheckFuelCommand;
import space.battle.core.command.action.MoveCommand;
import space.battle.core.command.action.RotateCommand;
import space.battle.core.command.macro.MacroCommand;
import space.battle.core.entity.Ship;
import space.battle.core.entity.UObject;
import space.battle.core.exception.CommandException;
import space.battle.core.support.Direction;
import space.battle.core.support.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MacroCommandTest {

    private UObject ship;

    private MoveAdapter moveAdapter;
    private RotateAdapter rotateAdapter;
    private VelocityAdapter velocityAdapter;
    private FuelAdapter fuelAdapter;

    private MacroCommand macroCommand;
    private MoveCommand moveCommand;
    private RotateCommand rotateCommand;
    private ChangeVelocityCommand changeVelocityCommand;

    @BeforeEach
    void before() {
        ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("rotable", true);
        ship.setProperty("position", new Vector(12, 5));
        ship.setProperty("velocity", new Vector(5, 0));
        ship.setProperty("fuel", 100);

        moveAdapter = new MoveAdapter(ship);
        moveCommand = new MoveCommand(moveAdapter);

        rotateAdapter = new RotateAdapter(ship);
        rotateCommand = new RotateCommand(rotateAdapter);

        velocityAdapter = new VelocityAdapter(ship);
        changeVelocityCommand = new ChangeVelocityCommand(velocityAdapter);

        fuelAdapter = new FuelAdapter(ship);
        CheckFuelCommand checkFuelCommand = new CheckFuelCommand(fuelAdapter);
        BurnFuelCommand burnFuelCommand = new BurnFuelCommand(fuelAdapter);

        macroCommand = new MacroCommand(List.of(checkFuelCommand, moveCommand, burnFuelCommand));
    }

    @Test
    void macroCommandTest() {
        ship.setProperty("burnFuelCount", 5);
        macroCommand.execute();

        assertEquals(new Vector(17, 5), moveAdapter.getPosition());
        assertEquals(95, fuelAdapter.getFuel());
    }

    @Test
    void errorMacroCommandTest() {
        assertThrows(CommandException.class, macroCommand::execute);
    }

    @Test
    void rotateAndChangeVelocityMacroCommandTest() {
        ship.setProperty("angularVelocity", -1);
        ship.setProperty("directionSections", 8);
        ship.setProperty("direction", new Direction());

        macroCommand = new MacroCommand(List.of(rotateCommand, changeVelocityCommand));
        macroCommand.execute();

        assertEquals(-1, ((Direction) ship.getProperty("direction")).getDirection());
        assertEquals(new Vector(3, 4), ship.getProperty("velocity"));
    }

    @Test
    void errorRotateAndChangeVelocityMacroCommandTest() {
        ship.setProperty("directionSections", 8);
        ship.setProperty("direction", new Direction());
        macroCommand = new MacroCommand(List.of(rotateCommand, changeVelocityCommand));

        assertThrows(UnsupportedOperationException.class, macroCommand::execute);
        assertEquals(new Vector(5, 0), ship.getProperty("velocity"));
    }
}

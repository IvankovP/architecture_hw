package space.battle.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.battle.core.adapter.FuelAdapter;
import space.battle.core.adapter.MoveAdapter;
import space.battle.core.command.Command;
import space.battle.core.command.action.BurnFuelCommand;
import space.battle.core.command.action.CheckFuelCommand;
import space.battle.core.command.action.MoveCommand;
import space.battle.core.command.support.LogCommand;
import space.battle.core.entity.Ship;
import space.battle.core.entity.UObject;
import space.battle.core.exception.CommandException;
import space.battle.core.exception.CommandExceptionHandler;
import space.battle.core.exception.ExceptionHandler;
import space.battle.core.exception.LogCommandHandler;
import space.battle.core.support.Vector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LogExceptionHandlerTest {

    private UObject uObject;
    private MoveAdapter moveAdapter;
    private FuelAdapter fuelAdapter;
    private Map<Class<? extends Command>, Map<Class<? extends Exception>, ExceptionHandler>> handlers;
    private Queue<Command> commands;
    private CommandExceptionHandler handler;

    @BeforeEach
    void before() {
        uObject = new Ship();
        uObject.setProperty("movable", true);
        uObject.setProperty("position", new Vector(12, 5));
        uObject.setProperty("velocity", new Vector(-7, 3));
        uObject.setProperty("fuel", 100);
        uObject.setProperty("burnFuelCount", 5);

        moveAdapter = new MoveAdapter(uObject);
        Command moveCommand = new MoveCommand(moveAdapter);

        fuelAdapter = new FuelAdapter(uObject);
        Command checkFuelCommand = new CheckFuelCommand(fuelAdapter);
        Command burnFuelCommand = new BurnFuelCommand(fuelAdapter);

        commands = new LinkedBlockingQueue<>(Arrays.asList(checkFuelCommand, moveCommand, burnFuelCommand));

        handlers = new HashMap<>();
        Map<Class<? extends Exception>, ExceptionHandler> checkFuelCommandHandlers = new HashMap<>();
        checkFuelCommandHandlers.put(CommandException.class, new LogCommandHandler(commands));
        handlers.put(CheckFuelCommand.class, checkFuelCommandHandlers);

        handler = new CommandExceptionHandler(commands, handlers);
    }

    @Test
    void logExceptionHandlerTest() {
        CommandRunner commandRunner = new CommandRunner(commands, handler);
        commandRunner.run();

        assertEquals(new Vector(5, 8), moveAdapter.getPosition());
        assertEquals(95, fuelAdapter.getFuel());
    }

    @Test
    void catchExceptionTest() {
        uObject.setProperty("fuel", 0);

        LogCommandHandler mock = mock(LogCommandHandler.class);
        handlers.get(CheckFuelCommand.class).put(CommandException.class, mock);

        CommandRunner commandRunner = new CommandRunner(commands, handler);
        commandRunner.run();

        verify(mock, atLeastOnce()).handle(any(), any());
    }

    @Test
    void addLogCommandTest() {
        commands.clear();
        handler.handle(new CommandException(), new CheckFuelCommand(null));

        assertEquals(1, commands.size());
        assertEquals(LogCommand.class, commands.peek().getClass());
    }
}

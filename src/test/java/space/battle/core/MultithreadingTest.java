package space.battle.core;

import org.junit.jupiter.api.Test;
import space.battle.core.adapter.FuelAdapter;
import space.battle.core.command.Command;
import space.battle.core.command.action.*;
import space.battle.core.command.macro.MacroCommand;
import space.battle.core.entity.Ship;
import space.battle.core.entity.UObject;
import space.battle.core.exception.CommandException;
import space.battle.core.exception.CommandExceptionHandler;
import space.battle.core.exception.ExceptionHandler;
import space.battle.core.exception.LogCommandHandler;
import space.battle.core.support.Vector;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MultithreadingTest {

    @Test
    void threadTest() throws InterruptedException {
        BlockingQueue<Command> commands = new LinkedBlockingQueue<>();
        BlockingQueue<Command> commandsForNewThread = new LinkedBlockingQueue<>();

        CommandExceptionHandler handler = createHandlers(commands, commandsForNewThread);
        Game game = getGame(commandsForNewThread, handler, 1);

        addStartCommand(commands, game);
        MacroCommand macroCommand = getMacroCommand();

        for (int i = 0; i < 10; i++) {
            commandsForNewThread.add(macroCommand);
        }

        start(commands, handler);

        System.out.println("Main thread is waiting...");
        game.getCountDownLatch().countDown();
        game.getCountDownLatch().await();

        assertNotEquals("main", game.getCurrentThread().getName());
        assertEquals(0, commandsForNewThread.size());
    }

    @Test
    void hardStopTest() throws InterruptedException {
        BlockingQueue<Command> commands = new LinkedBlockingQueue<>();
        BlockingQueue<Command> commandsForNewThread = new LinkedBlockingQueue<>();

        CommandExceptionHandler handler = createHandlers(commands, commandsForNewThread);
        Game game = getGame(commandsForNewThread, handler, 1);

        addStartCommand(commands, game);
        MacroCommand macroCommand = getMacroCommand();

        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                commandsForNewThread.put(new HardStopCommand(game));
            }
            commandsForNewThread.add(macroCommand);
        }

        int countCommand = commandsForNewThread.size();
        start(commands, handler);

        System.out.println("Main thread is waiting...");
        game.getCountDownLatch().countDown();
        game.getCountDownLatch().await();

        assertEquals(11, countCommand);
        assertEquals(5, commandsForNewThread.size());
    }

    @Test
    void softStopTest() throws InterruptedException {
        BlockingQueue<Command> commands = new LinkedBlockingQueue<>();
        BlockingQueue<Command> commandsForNewThread = new LinkedBlockingQueue<>();

        CommandExceptionHandler handler = createHandlers(commands, commandsForNewThread);
        Game game = getGame(commandsForNewThread, handler, 1);

        addStartCommand(commands, game);
        MacroCommand macroCommand = getMacroCommand();

        for (int i = 0; i < 21; i++) {
            if (i == 5) {
                commandsForNewThread.put(new SoftStopCommand(game));
            }
            commandsForNewThread.add(macroCommand);
        }

        int countCommand = commandsForNewThread.size();
        start(commands, handler);

        System.out.println("Main thread is waiting...");
        game.getCountDownLatch().countDown();
        game.getCountDownLatch().await();

        assertEquals(22, countCommand);
        assertEquals(0, commandsForNewThread.size());
    }

    private Game getGame(BlockingQueue<Command> commandsForNewThread, CommandExceptionHandler handler, int id) {
        Game game = new Game(commandsForNewThread, handler, id, Collections.emptyList());
        game.setStoppedFunction(() -> !game.getCommands().isEmpty());
        game.setCountDownLatch(new CountDownLatch(2));

        return game;
    }

    private void start(BlockingQueue<Command> commands, CommandExceptionHandler handler) {
        CommandRunner mainCommandRunner = new CommandRunner(commands, handler);
        mainCommandRunner.run();
    }

    private void addStartCommand(BlockingQueue<Command> commands, Game game) {
        RunNewThreadCommand runNewThreadCommand = new RunNewThreadCommand(game);
        try {
            commands.put(runNewThreadCommand);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private MacroCommand getMacroCommand() {
        FuelAdapter fuelAdapter = new FuelAdapter(createUObject());
        Command checkFuelCommand = new CheckFuelCommand(fuelAdapter);
        Command burnFuelCommand = new BurnFuelCommand(fuelAdapter);
        return new MacroCommand(List.of(checkFuelCommand, burnFuelCommand));
    }

    private UObject createUObject() {
        UObject uObject = new Ship();
        uObject.setProperty("movable", true);
        uObject.setProperty("position", new Vector(12, 5));
        uObject.setProperty("velocity", new Vector(-7, 3));
        uObject.setProperty("fuel", 100);
        uObject.setProperty("burnFuelCount", 5);
        return uObject;
    }

    private CommandExceptionHandler createHandlers(BlockingQueue<Command> commands, BlockingQueue<Command> commandsForNewThread) {
        Map<Class<? extends Command>, Map<Class<? extends Exception>, ExceptionHandler>> handlers = new HashMap<>();

        Map<Class<? extends Exception>, ExceptionHandler> commandHandlers = new HashMap<>();
        commandHandlers.put(CommandException.class, new LogCommandHandler(commandsForNewThread));

        handlers.put(CheckFuelCommand.class, commandHandlers);
        handlers.put(BurnFuelCommand.class, commandHandlers);
        handlers.put(MoveCommand.class, commandHandlers);
        handlers.put(MacroCommand.class, commandHandlers);

        return new CommandExceptionHandler(commands, handlers);
    }
}

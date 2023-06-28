package space.battle.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.battle.core.adapter.MoveAdapter;
import space.battle.core.command.Command;
import space.battle.core.command.action.MoveCommand;
import space.battle.core.command.action.RunNewThreadCommand;
import space.battle.core.command.collision.FindObjectZoneCommand;
import space.battle.core.command.macro.MacroCommand;
import space.battle.core.entity.Ship;
import space.battle.core.entity.UObject;
import space.battle.core.exception.CommandException;
import space.battle.core.exception.CommandExceptionHandler;
import space.battle.core.exception.ExceptionHandler;
import space.battle.core.exception.LogCommandHandler;
import space.battle.core.service.ZoneService;
import space.battle.core.support.Vector;
import space.battle.core.support.Zone;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

class CollisionTest {

    private List<UObject> objectList = new ArrayList<>();
    private List<Zone> map;
    private List<Zone> shiftMap;
    private UObject uObject;
    private BlockingQueue<Command> commands;
    private BlockingQueue<Command> commandsForNewThread;
    private CommandExceptionHandler handler;
    private Game game;

    @BeforeEach
    void before() {
        objectList.clear();
        map = ZoneService.createMap();
        shiftMap = ZoneService.createMap(5);
        IoC.register("map", "1", Arrays.asList(map, shiftMap));

        uObject = createObject(1, 5, 3, 4, 2);
        UObject uObject2 = createObject(2, 13, 7, -5, 1);
        UObject uObject3 = createObject(3, 14, 2, 6, 2);
        UObject uObject4 = createObject(4, 25, 4, 2, -1);
        UObject uObject5 = createObject(5, 28, 8, -1, 5);

        map.get(0).getObjects().add(uObject);
        map.get(1).getObjects().add(uObject2);
        map.get(1).getObjects().add(uObject3);
        map.get(2).getObjects().add(uObject4);
        map.get(2).getObjects().add(uObject5);

        shiftMap.get(0).getObjects().add(uObject);
        shiftMap.get(1).getObjects().add(uObject2);
        shiftMap.get(1).getObjects().add(uObject3);
        shiftMap.get(2).getObjects().add(uObject4);
        shiftMap.get(2).getObjects().add(uObject5);

        uObject.setProperty("zones", new HashSet<>(Set.of(map.get(0), shiftMap.get(0))));
        uObject2.setProperty("zones", new HashSet<>(Set.of(map.get(1), shiftMap.get(1))));
        uObject3.setProperty("zones", new HashSet<>(Set.of(map.get(1), shiftMap.get(1))));
        uObject4.setProperty("zones", new HashSet<>(Set.of(map.get(2), shiftMap.get(2))));
        uObject5.setProperty("zones", new HashSet<>(Set.of(map.get(2), shiftMap.get(2))));

        commands = new LinkedBlockingQueue<>();
        commandsForNewThread = new LinkedBlockingQueue<>();

        handler = createHandlers(commands, commandsForNewThread);
        game = getGame(commandsForNewThread, handler, 1);
    }

    @Test
    void noCollisionTest() throws InterruptedException {
        addStartCommand(commands, game);
        commandsForNewThread.add(getMacroCommand());
        start(commands, handler);

        System.out.println("Main thread is waiting...");
        game.getCountDownLatch().countDown();
        game.getCountDownLatch().await();

        assertEquals(new Vector(9, 5), uObject.getProperty("position"));
        objectList.forEach(obj -> assertFalse((Boolean) obj.getProperty("destroy")));
    }

    @Test
    void collisionTest() throws InterruptedException {
        commandsForNewThread.add(getMacroCommand());
        commandsForNewThread.add(getMacroCommand());

        addStartCommand(commands, game);
        start(commands, handler);

        System.out.println("Main thread is waiting...");
        game.getCountDownLatch().countDown();
        game.getCountDownLatch().await();

        assertEquals(new Vector(13, 7), uObject.getProperty("position"));

        //объекты столкнулись
        assertTrue((Boolean) uObject.getProperty("destroy"));
        assertTrue((Boolean) objectList.get(1).getProperty("destroy"));

        //не столкнулся с объектами
        assertFalse((Boolean) objectList.get(2).getProperty("destroy"));
        assertFalse((Boolean) objectList.get(3).getProperty("destroy"));
        assertFalse((Boolean) objectList.get(4).getProperty("destroy"));
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
        MoveAdapter moveAdapter = new MoveAdapter(uObject);
        MoveCommand move = new MoveCommand(moveAdapter);
        return new MacroCommand(List.of(move, new FindObjectZoneCommand(game, uObject)));
    }

    private CommandExceptionHandler createHandlers(BlockingQueue<Command> commands, BlockingQueue<Command> commandsForNewThread) {
        Map<Class<? extends Command>, Map<Class<? extends Exception>, ExceptionHandler>> handlers = new HashMap<>();

        Map<Class<? extends Exception>, ExceptionHandler> commandHandlers = new HashMap<>();
        commandHandlers.put(CommandException.class, new LogCommandHandler(commandsForNewThread));

        handlers.put(MoveCommand.class, commandHandlers);
        handlers.put(MacroCommand.class, commandHandlers);

        return new CommandExceptionHandler(commands, handlers);
    }

    private UObject createObject(int id, int x, int y, int shiftX, int shiftY) {
        UObject ship = new Ship();
        ship.setProperty("id", id);
        ship.setProperty("movable", true);
        ship.setProperty("position", new Vector(x, y));
        ship.setProperty("velocity", new Vector(shiftX, shiftY));
        ship.setProperty("destroy", false);

        objectList.add(ship);
        return ship;
    }
}

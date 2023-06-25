package space.battle.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.battle.core.adapter.MoveAdapter;
import space.battle.core.command.Command;
import space.battle.core.command.action.MoveCommand;
import space.battle.core.command.support.InterpretCommand;
import space.battle.core.entity.Ship;
import space.battle.core.entity.UObject;
import space.battle.core.exception.CommandExceptionHandler;
import space.battle.core.support.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExchangeSystemTest {

    @BeforeEach
    void before() {
        IoC.register("game", "1", createGame(1));
        IoC.register("game", "2", createGame(2));
        IoC.register("uObject", "1", createUObject(1, 1));
        IoC.register("uObject", "2", createUObject(2, 1));
        IoC.register("uObject", "3", createUObject(3, 2));
        BiFunction<UObject, Map<String, String>, Command> function = (uObject, commandArgs) -> new MoveCommand(new MoveAdapter(uObject));
        IoC.register("MoveCommand", function);
    }

    private Game createGame(int id) {
        LinkedBlockingQueue<Command> commands = new LinkedBlockingQueue<>();
        Game game = new Game(commands, new CommandExceptionHandler(commands, new HashMap<>()), id);
        game.setStoppedFunction(() -> !game.getCommands().isEmpty());
        return game;
    }

    private UObject createUObject(int id, int gameId) {
        UObject ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("position", new Vector(12 + id, 5 + id));
        ship.setProperty("velocity", new Vector(-7 + id, 3 + id));
        ship.setProperty("fuel", 100 * id);
        ship.setProperty("burnFuelCount", 5 + id);
        ship.setProperty("gameId", gameId);

        return ship;
    }

    @Test
    void createInterpretCommandTest() {
//        Формат сообщения
//        {
//            "token", "ADLFSDFLJLSKDFLSDVNLSCALSDKASLDJSJDFKHSDJFSLDFJSLDJFSLDFJ",
//            "gameId", "1",
//            "objectId", "2",
//            "commandId", "MoveCommand",
//            "commandArgs", "{'arg':'test', 'arg2':'test2'}"
//        }

        Map<String, String> commandArgs = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        param.put("gameId", "1");
        param.put("objectId", "2");
        param.put("commandId", "MoveCommand");

        Command command = new InterpretCommand(param, commandArgs);
        command.execute();

        Game game = IoC.resolve("game", "1");
        Game game2 = IoC.resolve("game", "2");

        assertEquals(1, game.getCommands().size());
        assertEquals(0, game2.getCommands().size());
    }

    @Test
    void runCommandTest() {
        Map<String, String> commandArgs = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        param.put("gameId", "1");
        param.put("objectId", "2");
        param.put("commandId", "MoveCommand");

        Command command = new InterpretCommand(param, commandArgs);

        Game game = IoC.resolve("game", "1");
        game.getCommands().add(command);
        game.run();

        UObject uObject = IoC.resolve("uobject", "2");
        Vector position = (Vector) uObject.getProperty("position");

        assertEquals(0, game.getCommands().size());
        assertEquals(9, position.getX());
        assertEquals(12, position.getY());
    }
}

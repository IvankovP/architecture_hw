package space.battle.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.battle.core.adapter.FireAdapter;
import space.battle.core.adapter.MoveAdapter;
import space.battle.core.command.Command;
import space.battle.core.command.action.FireCommand;
import space.battle.core.command.action.MoveCommand;
import space.battle.core.command.support.InterpretCommand;
import space.battle.core.entity.Ship;
import space.battle.core.entity.UObject;
import space.battle.core.exception.CommandExceptionHandler;
import space.battle.core.support.Vector;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterpreterTest {

    //        Формат сообщения
    //        {
    //            "token", "ADLFSDFLJLSKDFLSDVNLSCALSDKASLDJSJDFKHSDJFSLDFJSLDJFSLDFJ",
    //            "gameId", "1",
    //            "objectId", "2",
    //            "commandId", "MoveCommand",
    //            "commandArgs", "{'arg':'test', 'arg2':'test2'}"
    //        }

    private final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJnYW1lSWQiOjEsInN1YiI6ImF1dGgtc2VydmljZSIsIm5iZiI6MTY4NzkzODI0MCwiaXNzIjoiYXV0aC1zZXJ2aWNlIiwidXNlck5hbWUiOiJUZXN0MiIsImV4cCI6MTcxOTQ3NDI0MCwiaWF0IjoxNjg3OTM4MjQwLCJqdGkiOiIzZjYwYjI0My03ZTU3LTQwZGMtYmZiMi03NDk3YjI4NzEyMzYifQ.3JL4b4hBlm5c373KdmcRaVzXdMeP3feaejwksbaUdMQ";

    @BeforeEach
    void before() {
        BiFunction<UObject, Map<String, String>, Command> function = (uObject, commandArgs) -> new MoveCommand(new MoveAdapter(uObject));
        IoC.register("MoveCommand", function);

        BiFunction<UObject, Map<String, String>, Command> fireCommand = (uObject, commandArgs) -> new FireCommand(new FireAdapter(uObject));
        IoC.register("FireCommand", fireCommand);

        Scope scope = new Scope("Test");
        scope.getUObjectSet().add(createUObject(2, 1));
        Scope scope2 = new Scope("Test2");
        scope2.getUObjectSet().add(createUObject(2, 1));
        Scope scope3 = new Scope("Test3");
        scope3.getUObjectSet().add(createUObject(3, 2));

        IoC.register("game", "1", createGame(1, List.of(scope, scope2)));
        IoC.register("game", "2", createGame(2, Collections.singletonList(scope3)));
    }

    private Game createGame(int id, List<Scope> scopes) {
        LinkedBlockingQueue<Command> commands = new LinkedBlockingQueue<>();
        Game game = new Game(commands, new CommandExceptionHandler(commands, new HashMap<>()), id, scopes);
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
        ship.setProperty("gameId", String.valueOf(gameId));
        ship.setProperty("id", String.valueOf(id));
        ship.setProperty("countRockets", 5);

        return ship;
    }

    @Test
    void checkDontMoveStrangerObjectTest() {
        Map<String, String> commandArgs = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        param.put("gameId", "1");
        param.put("objectId", "2");
        param.put("commandId", "MoveCommand");
        param.put("token", token);

        Command command = new InterpretCommand(param, commandArgs);

        Game game = IoC.resolve("game", "1");
        game.getCommands().add(command);
        game.run();

        UObject uObject1 = game.getScopes().get(0).getUObjectSet().stream().findFirst().get();
        UObject uObject2 = game.getScopes().get(1).getUObjectSet().stream().findFirst().get();

        Vector position1 = (Vector) uObject1.getProperty("position");
        Vector position2 = (Vector) uObject2.getProperty("position");

        assertEquals(0, game.getCommands().size());

        //чужой не сдвинулся
        assertEquals(14, position1.getX());
        assertEquals(7, position1.getY());

        //свой сдвинулся
        assertEquals(9, position2.getX());
        assertEquals(12, position2.getY());
    }

    @Test
    void fireCommandTest() {
        Map<String, String> commandArgs = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        param.put("gameId", "1");
        param.put("objectId", "2");
        param.put("commandId", "FireCommand");
        param.put("token", token);

        Command command = new InterpretCommand(param, commandArgs);

        Game game = IoC.resolve("game", "1");
        game.getCommands().add(command);
        game.run();

        UObject uObject1 = game.getScopes().get(0).getUObjectSet().stream().findFirst().get();
        UObject uObject2 = game.getScopes().get(1).getUObjectSet().stream().findFirst().get();

        int countRockets1 = (int) uObject1.getProperty("countRockets");
        int countRockets2 = (int) uObject2.getProperty("countRockets");

        assertEquals(0, game.getCommands().size());

        //чужой не выстрелил
        assertEquals(5, countRockets1);

        //свой выстрелил
        assertEquals(4, countRockets2);
    }
}

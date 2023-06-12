package space.battle.core.command.support;

import space.battle.core.Game;
import space.battle.core.IoC;
import space.battle.core.command.Command;
import space.battle.core.entity.UObject;

import java.util.Map;
import java.util.function.BiFunction;

public class InterpretCommand implements Command {

    private final Map<String, String> param;
    private final Map<String, String> commandArgs;

    public InterpretCommand(Map<String, String> param, Map<String, String> commandArgs) {
        this.param = param;
        this.commandArgs = commandArgs;
    }

    @Override
    public void execute() {
        String gameId = param.get("gameId");
        String objectId = param.get("objectId");
        String commandId = param.get("commandId");

        Game game = IoC.resolve("game", gameId);
        UObject uObject = IoC.resolve("uobject", objectId);
        BiFunction<UObject, Map<String, String>, Command> function = IoC.resolve(commandId);
        Command command = function.apply(uObject, commandArgs);
        game.getCommands().add(command);
    }
}

package space.battle.core.command.support;

import space.battle.core.Game;
import space.battle.core.IoC;
import space.battle.core.Scope;
import space.battle.core.command.Command;
import space.battle.core.entity.UObject;
import space.battle.core.utils.GameUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
        String userName = GameUtils.getUserName(param.get("token"));

        Game game = IoC.resolve("game", gameId);
        Optional<Scope> userScope = game.getScopes().stream()
                .filter(scope -> scope.getUserName().equalsIgnoreCase(userName))
                .findFirst();

        if (userScope.isEmpty()) {
            return;
        }

        UObject uObject = userScope.get().getUObjectSet().stream()
                .filter(obj -> Objects.equals(objectId, obj.getProperty("id")))
                .findFirst()
                .orElse(null);

        if (uObject == null) {
            return;
        }

        BiFunction<UObject, Map<String, String>, Command> function = IoC.resolve(commandId);
        Command command = function.apply(uObject, commandArgs);
        game.getCommands().add(command);
    }
}

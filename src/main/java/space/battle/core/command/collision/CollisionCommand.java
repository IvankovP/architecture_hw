package space.battle.core.command.collision;

import space.battle.core.Game;
import space.battle.core.command.Command;
import space.battle.core.command.macro.MacroCommand;
import space.battle.core.entity.UObject;

import java.util.List;
import java.util.stream.Collectors;

public class CollisionCommand implements Command {

    private final Game game;
    private final UObject uObject;
    private final List<UObject> checkedObjects;

    public CollisionCommand(Game game, UObject uObject, List<UObject> checkedObjects) {
        this.game = game;
        this.uObject = uObject;
        this.checkedObjects = checkedObjects;
    }

    @Override
    public void execute() {
        System.out.println("Create collision command");

        List<Command> collisionCommands = checkedObjects.stream()
                .map(obj -> new CheckObjectCollisionCommand(uObject, obj))
                .collect(Collectors.toList());

        Command checkCollision = new MacroCommand(collisionCommands);
        game.getCommands().add(checkCollision);
    }
}

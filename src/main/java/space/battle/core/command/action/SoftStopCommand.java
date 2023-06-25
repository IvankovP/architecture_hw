package space.battle.core.command.action;

import space.battle.core.Game;
import space.battle.core.command.Command;

public class SoftStopCommand implements Command {

    private final Game game;

    public SoftStopCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.setStoppedFunction(() -> !game.getCommands().isEmpty());
        System.out.println("Soft stop...");
    }
}

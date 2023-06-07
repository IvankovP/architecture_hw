package space.battle.core.command.action;

import space.battle.core.Game;
import space.battle.core.command.Command;

public class HardStopCommand implements Command {

    private final Game game;

    public HardStopCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.setStoppedFunction(() -> false);
        System.out.println("Hard stop...");
    }
}

package space.battle.core.command.support;

import space.battle.core.GameState;
import space.battle.core.command.Command;

public class HardStopStateCommand implements Command {

    private final GameState game;

    public HardStopStateCommand(GameState game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.getState().setNextState(null);
        System.out.println("Hard stop state...");
    }
}

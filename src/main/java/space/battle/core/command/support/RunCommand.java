package space.battle.core.command.support;

import space.battle.core.GameState;
import space.battle.core.command.Command;
import space.battle.core.state.StandardState;

public class RunCommand implements Command {

    private final GameState game;

    public RunCommand(GameState game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.getState().setNextState(new StandardState());
        System.out.println("Set Standard state...");
    }
}

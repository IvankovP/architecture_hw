package space.battle.core.command.support;

import space.battle.core.GameState;
import space.battle.core.command.Command;
import space.battle.core.state.MoveToState;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

public class MoveToCommand implements Command {

    private final GameState game;
    private final BlockingQueue<Command> newCommandsQueue;

    public MoveToCommand(GameState game, BlockingQueue<Command> newCommandsQueue) {
        this.game = game;
        this.newCommandsQueue = newCommandsQueue;
    }

    @Override
    public void execute() {
        game.getState().setNextState(new MoveToState(newCommandsQueue, Arrays.asList(HardStopStateCommand.class, RunCommand.class)));
        System.out.println("Set MoveTo state...");
    }
}

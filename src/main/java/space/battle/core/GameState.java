package space.battle.core;

import space.battle.core.command.Command;
import space.battle.core.exception.ExceptionHandler;
import space.battle.core.state.StandardState;
import space.battle.core.state.State;
import space.battle.core.utils.ThreadUtils;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class GameState extends Game implements Runnable {

    private State state;

    public GameState(BlockingQueue<Command> commands, ExceptionHandler handler, int id, List<String> userNames, State state) {
        super(commands, handler, id, userNames);
        this.state = state;
    }

    public State getState() {
        return state;
    }

    @Override
    public void run() {
        currentThread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + " is running");

        while (state != null && state.handle() != null) {
            state.handle().run().accept(commands, handler);
        }

        System.out.println(Thread.currentThread().getName() + " is stopped");
        ThreadUtils.downAndAwaitCountDownLatch(countDownLatch);
    }
}
package space.battle.core.state;

import space.battle.core.command.Command;
import space.battle.core.exception.ExceptionHandler;
import space.battle.core.utils.ThreadUtils;

import java.util.concurrent.BlockingQueue;
import java.util.function.BiConsumer;

public class StandardState implements State {

    private State nextState;

    public StandardState() {
        this.nextState = this;
    }

    @Override
    public State handle() {
        return nextState;
    }

    @Override
    public BiConsumer<BlockingQueue<Command>, ExceptionHandler> run() {
        return (commands, handler) -> {
            Command command = null;
            try {
                command = commands.take();
                System.out.println(Thread.currentThread().getName() + " Take!!");
                command.execute();

                ThreadUtils.sleep(300);
            } catch (Exception e) {
                handler.handle(e, command);
            }
        };
    }

    @Override
    public void setNextState(State nextState) {
        this.nextState = nextState;
    }
}

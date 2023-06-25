package space.battle.core.state;

import space.battle.core.command.Command;
import space.battle.core.exception.ExceptionHandler;

import java.util.concurrent.BlockingQueue;
import java.util.function.BiConsumer;

public interface State {
    State handle();
    BiConsumer<BlockingQueue<Command>, ExceptionHandler> run();
    void setNextState(State nextState);
}

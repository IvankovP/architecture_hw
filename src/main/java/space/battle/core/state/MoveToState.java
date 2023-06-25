package space.battle.core.state;

import space.battle.core.command.Command;
import space.battle.core.exception.ExceptionHandler;
import space.battle.core.utils.ThreadUtils;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.function.BiConsumer;

public class MoveToState implements State {

    private State nextState;
    private final BlockingQueue<Command> queue;
    private final List<Class<? extends Command>> supportCommands;

    public MoveToState(BlockingQueue<Command> queue, List<Class<? extends Command>> supportCommand) {
        this.queue = queue;
        this.supportCommands = supportCommand;
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
                if (supportCommands.contains(command.getClass())) {
                    command.execute();
                } else {
                    System.out.println(Thread.currentThread().getName() + " send to new queue!!");
                    queue.put(command);
                }

                ThreadUtils.sleep(300);
            } catch (Exception e) {
                handler.handle(e, command);
            }
        };
    }

    @Override
    public void setNextState(State nextState) {

    }
}

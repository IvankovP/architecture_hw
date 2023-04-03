package space.battle.core.exception;

import space.battle.core.command.Command;
import space.battle.core.command.support.LogCommand;

import java.util.Queue;

public class LogCommandHandler implements ExceptionHandler {

    private final Queue<Command> commandQueue;

    public LogCommandHandler(Queue<Command> commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public void handle(Exception ex, Command command) {
        commandQueue.add(new LogCommand(ex));
    }
}

package space.battle.core.exception;

import space.battle.core.command.Command;
import space.battle.core.command.support.RepeatCommand;

import java.util.Queue;

public class RepeatCommandHandler implements ExceptionHandler {

    private final Queue<Command> commandQueue;

    public RepeatCommandHandler(Queue<Command> commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public void handle(Exception ex, Command command) {
        if (commandQueue != null) {
            commandQueue.add(new RepeatCommand(command));
        }
    }
}

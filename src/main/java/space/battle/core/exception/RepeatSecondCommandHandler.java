package space.battle.core.exception;

import space.battle.core.command.Command;
import space.battle.core.command.support.RepeatSecondCommand;

import java.util.Queue;

public class RepeatSecondCommandHandler implements ExceptionHandler {

    private final Queue<Command> commandQueue;

    public RepeatSecondCommandHandler(Queue<Command> commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public void handle(Exception ex, Command command) {
        if (commandQueue != null) {
            commandQueue.add(new RepeatSecondCommand(command));
        }
    }
}
package space.battle.core.exception;

import space.battle.core.command.Command;

import java.util.Map;
import java.util.Queue;

public class CommandExceptionHandler implements ExceptionHandler {

    private final Queue<Command> commandQueue;
    private final Map<Class<? extends Command>, Map<Class<? extends Exception>, ExceptionHandler>> handlers;

    public CommandExceptionHandler(Queue<Command> commandQueue, Map<Class<? extends Command>, Map<Class<? extends Exception>, ExceptionHandler>> handlers) {
        this.commandQueue = commandQueue;
        this.handlers = handlers;
    }

    @Override
    public void handle(Exception ex, Command command) {
        if (commandQueue != null) {
            ExceptionHandler handler = getHandler(ex, command);
            if (handler != null) {
                handler.handle(ex, command);
            }
        }
    }

    private ExceptionHandler getHandler(Exception ex, Command command) {
        if (!handlers.containsKey(command.getClass())) {
            return null;
        }
        return handlers.get(command.getClass()).get(ex.getClass());
    }
}

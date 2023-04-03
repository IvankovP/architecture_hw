package space.battle.core.command.macro;

import space.battle.core.command.Command;
import space.battle.core.exception.ExceptionHandler;

import java.util.Queue;

public class MacroCommandWithExceptionHandler implements Command {

    private final Queue<Command> commands;
    ExceptionHandler handler;

    public MacroCommandWithExceptionHandler(Queue<Command> commands, ExceptionHandler handler) {
        this.commands = commands;
        this.handler = handler;
    }

    @Override
    public void execute() {
        while (!commands.isEmpty()) {
            Command command = commands.poll();
            try {
                command.execute();
            } catch (Exception ex) {
                handler.handle(ex, command);
            }
        }
    }
}
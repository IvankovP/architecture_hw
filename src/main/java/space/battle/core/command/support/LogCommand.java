package space.battle.core.command.support;

import space.battle.core.command.Command;

public class LogCommand implements Command {

    private final Exception exception;

    public LogCommand(Exception exception) {
        this.exception = exception;
    }

    @Override
    public void execute() {
        System.out.println(exception == null || exception.getMessage() == null || exception.getMessage().isBlank() ? "Unknown exception" : exception.getMessage());
    }
}

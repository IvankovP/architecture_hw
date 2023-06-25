package space.battle.core.exception;

import space.battle.core.command.Command;

public interface ExceptionHandler {
    void handle(Exception ex, Command command);
}

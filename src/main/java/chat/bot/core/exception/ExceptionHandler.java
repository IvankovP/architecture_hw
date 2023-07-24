package chat.bot.core.exception;

import chat.bot.core.command.Command;

public interface ExceptionHandler {
    void handle(Exception ex, Command command);
}

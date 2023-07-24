package chat.bot.core.exception;

public class CommandException extends RuntimeException {
    public CommandException(String msg) {
        super(msg);
    }

    public CommandException() {
        super();
    }
}

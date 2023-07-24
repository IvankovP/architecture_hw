package chat.bot.core.command.support;

import chat.bot.core.command.Command;

public class RepeatCommand implements Command {

    private final Command command;

    public RepeatCommand(Command command) {
        this.command = command;
    }

    @Override
    public void execute() throws Exception {
        if (command != null) {
            command.execute();
        }
    }
}

package space.battle.core.command.support;

import space.battle.core.command.Command;

public class RepeatCommand implements Command {

    private final Command command;

    public RepeatCommand(Command command) {
        this.command = command;
    }

    @Override
    public void execute() {
        if (command != null) {
            command.execute();
        }
    }
}

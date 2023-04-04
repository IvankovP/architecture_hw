package space.battle.core.command.support;

import space.battle.core.command.Command;

public class RepeatSecondCommand implements Command {

    private final Command command;

    public RepeatSecondCommand(Command command) {
        this.command = command;
    }

    @Override
    public void execute() {
        if (command != null) {
            command.execute();
        }
    }
}

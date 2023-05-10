package space.battle.core.command.action;

import space.battle.core.CommandThreadRunner;
import space.battle.core.command.Command;

public class HardStopCommand implements Command {

    private final CommandThreadRunner runner;

    public HardStopCommand(CommandThreadRunner runner) {
        this.runner = runner;
    }

    @Override
    public void execute() {
        runner.setStoppedFunction(() -> false);
        System.out.println("Hard stop...");
    }
}

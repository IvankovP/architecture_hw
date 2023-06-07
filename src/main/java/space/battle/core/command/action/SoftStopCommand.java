package space.battle.core.command.action;

import space.battle.core.Game;
import space.battle.core.command.Command;

public class SoftStopCommand implements Command {

    private final Game runner;

    public SoftStopCommand(Game runner) {
        this.runner = runner;
    }

    @Override
    public void execute() {
        runner.setStoppedFunction(() -> !runner.getCommands().isEmpty());
        System.out.println("Soft stop...");
    }
}

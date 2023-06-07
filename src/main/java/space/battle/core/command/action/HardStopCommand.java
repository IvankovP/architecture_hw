package space.battle.core.command.action;

import space.battle.core.Game;
import space.battle.core.command.Command;

public class HardStopCommand implements Command {

    private final Game runner;

    public HardStopCommand(Game runner) {
        this.runner = runner;
    }

    @Override
    public void execute() {
        runner.setStoppedFunction(() -> false);
        System.out.println("Hard stop...");
    }
}

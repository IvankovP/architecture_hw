package space.battle.core.command.action;

import space.battle.core.command.Command;

public class RunNewThreadCommand implements Command {

    private final Runnable runner;

    public RunNewThreadCommand(Runnable runner) {
        this.runner = runner;
    }

    @Override
    public void execute() {
        Thread thread = new Thread(runner);
        thread.start();
    }
}

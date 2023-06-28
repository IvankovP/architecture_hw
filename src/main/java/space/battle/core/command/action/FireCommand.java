package space.battle.core.command.action;

import space.battle.core.adapter.FireAdapter;
import space.battle.core.command.Command;

public class FireCommand implements Command {

    private final FireAdapter fireAdapter;

    public FireCommand(FireAdapter fireAdapter) {
        this.fireAdapter = fireAdapter;
    }

    @Override
    public void execute() {
        fireAdapter.fire();
    }
}

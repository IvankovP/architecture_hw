package space.battle.core.command.collision;

import space.battle.core.command.Command;
import space.battle.core.entity.UObject;
import space.battle.core.service.ZoneService;

public class CheckObjectCollisionCommand implements Command {

    private final UObject uObject;
    private final UObject uObject2;

    public CheckObjectCollisionCommand(UObject uObject, UObject uObject2) {
        this.uObject = uObject;
        this.uObject2 = uObject2;
    }

    @Override
    public void execute() {
        System.out.println("Check objects with id " + uObject.getProperty("id") + " and id " + uObject2.getProperty("id"));
        if (ZoneService.checkCollision(uObject, uObject2)) {
            uObject.setProperty("destroy", true);
            uObject2.setProperty("destroy", true);
            System.out.println("Objects was destroyed");
        }
    }
}

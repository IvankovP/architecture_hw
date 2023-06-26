package space.battle.core.command.collision;

import space.battle.core.Game;
import space.battle.core.IoC;
import space.battle.core.command.Command;
import space.battle.core.entity.UObject;
import space.battle.core.support.Vector;
import space.battle.core.zone.Zone;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindObjectZoneCommand implements Command {

    private final Game game;
    private final UObject uObject;

    public FindObjectZoneCommand(Game game, UObject uObject) {
        this.game = game;
        this.uObject = uObject;
    }

    @Override
    public void execute() {
        System.out.println("Find zone for object id " + uObject.getProperty("id"));

        List<List<Zone>> map = IoC.resolve("map" + game.getId());
        Set<Zone> foundedZones = new HashSet<>();

        for (List<Zone> zones : map) {
            for (Zone zone : zones) {
                if (zone.contains((Vector) uObject.getProperty("position"))) {
                    foundedZones.add(zone);
                }
            }
        }

        new ChangeZoneCommand(game, uObject, foundedZones).execute();
    }
}

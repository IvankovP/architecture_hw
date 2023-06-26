package space.battle.core.command.collision;

import space.battle.core.Game;
import space.battle.core.command.Command;
import space.battle.core.entity.UObject;
import space.battle.core.zone.Zone;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChangeZoneCommand implements Command {

    private final UObject uObject;
    private final Set<Zone> foundedZones;
    private final Game game;

    public ChangeZoneCommand(Game game, UObject uObject, Set<Zone> foundedZones) {
        this.uObject = uObject;
        this.foundedZones = foundedZones;
        this.game = game;
    }

    @Override
    public void execute() {
        System.out.println("Change zone for object id " + uObject.getProperty("id"));

        //удаляем объект из текущих зон, если он их покинул
        Set<Zone> currentZones = (Set<Zone>) uObject.getProperty("zones");
        List<Zone> toRemove = new ArrayList<>();

        for (Zone currentZone : currentZones) {
            if (!foundedZones.contains(currentZone)) {
                currentZone.getObjects().remove(uObject);
                toRemove.add(currentZone);
            }
        }
        currentZones.removeAll(toRemove);

        List<UObject> checkedObjects = new ArrayList<>();
        foundedZones.forEach(zone -> {
            //добавляем все объекты новых зон для проверки коллизии
            checkedObjects.addAll(zone.getObjects());
            //добавляем текущий объект в новые зоны
            zone.getObjects().add(uObject);
            currentZones.add(zone);
            //себя не проверяем
            checkedObjects.remove(uObject);
        });

        //макро команда для проверки коллизий
        new CollisionCommand(game, uObject, checkedObjects).execute();
    }
}

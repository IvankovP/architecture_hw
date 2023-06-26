package space.battle.core.service;

import space.battle.core.entity.UObject;
import space.battle.core.support.Vector;
import space.battle.core.zone.Zone;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ZoneService {
    public static List<Zone> createMap() {
        return createMap(0);
    }

    public static List<Zone> createMap(int shift) {
        List<Zone> zones = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Zone zone = new Zone();
            zone.setBegin(new Vector(i * zone.getZoneSize() + shift, 0 + shift));
            zone.setEnd(new Vector(zone.getBegin().getX() + zone.getZoneSize() - 1, zone.getBegin().getY() + zone.getZoneSize() - 1));

            zones.add(zone);
        }

        return zones;
    }

    public static boolean checkCollision(UObject object1, UObject object2) {
        return Objects.equals(object1.getProperty("position"), object2.getProperty("position"));
    }
}

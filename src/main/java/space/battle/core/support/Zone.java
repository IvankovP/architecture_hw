package space.battle.core.support;

import space.battle.core.entity.UObject;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Zone {
    private int zoneSize = 10;
    private Vector begin;
    private Vector end;
    private Set<UObject> objects = new HashSet<>();

    public Vector getBegin() {
        return begin;
    }

    public void setBegin(Vector begin) {
        this.begin = begin;
    }

    public Vector getEnd() {
        return end;
    }

    public void setEnd(Vector end) {
        this.end = end;
    }

    public Set<UObject> getObjects() {
        return objects;
    }

    public void setObjects(Set<UObject> objects) {
        this.objects = objects;
    }

    public int getZoneSize() {
        return zoneSize;
    }

    public void setZoneSize(int zoneSize) {
        this.zoneSize = zoneSize;
    }

    public boolean contains(Vector position) {
        return position != null &&
                position.getX() >= begin.getX() && position.getX() <= end.getX() &&
                position.getY() >= begin.getY() && position.getY() <= end.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zone zone = (Zone) o;
        return begin.equals(zone.begin) && end.equals(zone.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(begin, end);
    }
}

package space.battle.core.support;

public class Direction {

    private int direct;

    public Direction next(int angularVelocity, int directionSections) {
        this.direct = (angularVelocity + direct) % directionSections;
        return this;
    }

    public int getDirection() {
        return direct;
    }
}

package space.battle.core.movement;

import space.battle.core.support.Direction;

public interface Rotable {
    Direction getDirection();

    void setDirection(Direction next);

    int getAngularVelocity();

    int getDirectionSections();
}

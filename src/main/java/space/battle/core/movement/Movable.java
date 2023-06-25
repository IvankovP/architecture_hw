package space.battle.core.movement;

import space.battle.core.support.Vector;

public interface Movable {
    Vector getPosition();
    Vector getVelocity();
    void setPosition(Vector newPosition);
}

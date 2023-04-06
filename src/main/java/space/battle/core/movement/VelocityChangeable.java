package space.battle.core.movement;

import space.battle.core.support.Vector;

public interface VelocityChangeable {
    void setVelocity(Vector newVelocity);
    Vector calculateVelocity();
}

package space.battle.core.adapter;

import space.battle.core.entity.UObject;
import space.battle.core.movement.Movable;
import space.battle.core.support.Vector;

public class MoveAdapter implements Movable {

    private final UObject movableObject;

    public MoveAdapter(UObject movableObject) {
        this.movableObject = movableObject;
    }

    @Override
    public Vector getPosition() {
        return (Vector) getProperty("position");
    }

    @Override
    public Vector getVelocity() {
        return (Vector) getProperty("velocity");
    }

    @Override
    public void setPosition(Vector newPosition) {
        if (!isMovable(movableObject)) {
            throw new UnsupportedOperationException();
        }
        movableObject.setProperty("position", newPosition);
    }

    private boolean isMovable(UObject movableObject) {
        return movableObject != null && Boolean.TRUE.equals(movableObject.getProperty("movable"));
    }

    private Object getProperty(String name) {
        Object property = movableObject.getProperty(name);
        if (property != null) {
            return property;
        }
        throw new UnsupportedOperationException();
    }
}

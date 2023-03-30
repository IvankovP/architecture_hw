package space.battle.core.support;

import space.battle.core.movement.Movable;

import java.util.Map;

public class MoveAdapter implements Movable {

    private final Map<String, Object> movableObject;

    public MoveAdapter(Map<String, Object> movableObject) {
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
        movableObject.put("position", newPosition);
    }

    private Object getProperty(String name) {
        if (movableObject.containsKey(name)) {
            return movableObject.get(name);
        }
        throw new UnsupportedOperationException();
    }
}

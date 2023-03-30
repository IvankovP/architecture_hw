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
        return (Vector) movableObject.get("position");
    }

    @Override
    public Vector getVelocity() {
        return (Vector) movableObject.get("velocity");
    }

    @Override
    public void setPosition(Vector newPosition) {
        movableObject.put("position", newPosition);
    }
}

package space.battle.core;

import org.junit.jupiter.api.Test;
import space.battle.core.movement.Move;
import space.battle.core.support.MoveAdapter;
import space.battle.core.support.Vector;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveTest {

    @Test
    void moveTest() {
        Map<String, Object> ship = new HashMap<>();
        ship.put("position", new Vector(12, 5));
        ship.put("velocity", new Vector(-7, 3));

        MoveAdapter moveAdapter = new MoveAdapter(ship);
        Move move = new Move(moveAdapter);
        move.execute();

        assertEquals(new Vector(5, 8), ship.get("position"));
    }
}

package space.battle.core;

import org.junit.jupiter.api.Test;
import space.battle.core.movement.Move;
import space.battle.core.support.MoveAdapter;
import space.battle.core.support.Vector;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void errorGettingPositionTest() {
        Map<String, Object> space = new HashMap<>();

        MoveAdapter moveAdapter = new MoveAdapter(space);
        Move move = new Move(moveAdapter);

        assertThrows(UnsupportedOperationException.class, move::execute);
    }

    @Test
    void errorGettingVelocityTest() {
        Map<String, Object> planet = new HashMap<>();
        planet.put("position", new Vector(55, 11));

        MoveAdapter moveAdapter = new MoveAdapter(planet);
        Move move = new Move(moveAdapter);

        assertThrows(UnsupportedOperationException.class, move::execute);
    }
}

package space.battle.core;

import org.junit.jupiter.api.Test;
import space.battle.core.entity.Ship;
import space.battle.core.entity.Planet;
import space.battle.core.entity.UObject;
import space.battle.core.movement.Move;
import space.battle.core.support.MoveAdapter;
import space.battle.core.support.Vector;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @Test
    void moveTest() {
        UObject ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("position", new Vector(12, 5));
        ship.setProperty("velocity", new Vector(-7, 3));

        MoveAdapter moveAdapter = new MoveAdapter(ship);
        Move move = new Move(moveAdapter);
        move.execute();

        assertEquals(new Vector(5, 8), ship.getProperty("position"));
    }

    @Test
    void errorGettingPositionTest() {
        UObject ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("velocity", new Vector(-7, 3));

        MoveAdapter moveAdapter = new MoveAdapter(ship);
        Move move = new Move(moveAdapter);

        assertThrows(UnsupportedOperationException.class, move::execute);
    }

    @Test
    void errorGettingVelocityTest() {
        UObject ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("position", new Vector(12, 5));

        MoveAdapter moveAdapter = new MoveAdapter(ship);
        Move move = new Move(moveAdapter);

        assertThrows(UnsupportedOperationException.class, move::execute);
    }

    @Test
    void errorSettingPositionTest() {
        UObject planet = new Planet();
        planet.setProperty("movable", false);
        planet.setProperty("position", new Vector(100, 100));
        planet.setProperty("velocity", new Vector(0, 0));

        MoveAdapter moveAdapter = new MoveAdapter(planet);
        Move move = new Move(moveAdapter);

        assertThrows(UnsupportedOperationException.class, move::execute);
    }
}

package space.battle.core;

import org.junit.jupiter.api.Test;
import space.battle.core.entity.Ship;
import space.battle.core.entity.UObject;
import space.battle.core.command.RotateCommand;
import space.battle.core.support.Direction;
import space.battle.core.adapter.RotateAdapter;
import space.battle.core.support.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RotateTest {

    @Test
    void rotateTest() {
        UObject ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("rotable", true);
        ship.setProperty("position", new Vector(12, 5));
        ship.setProperty("velocity", new Vector(-7, 3));
        ship.setProperty("angularVelocity", -1);
        ship.setProperty("directionSections", 8);
        ship.setProperty("direction", new Direction());

        RotateAdapter rotateAdapter = new RotateAdapter(ship);
        RotateCommand rotate = new RotateCommand(rotateAdapter);
        rotate.execute();
        rotate.execute();

        assertEquals(-2, ((Direction) ship.getProperty("direction")).getDirection());
    }

    @Test
    void errorGettingAngularVelocityTest() {
        UObject ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("rotable", true);
        ship.setProperty("position", new Vector(12, 5));
        ship.setProperty("velocity", new Vector(-7, 3));
        ship.setProperty("directionSections", 8);
        ship.setProperty("direction", new Direction());

        RotateAdapter rotateAdapter = new RotateAdapter(ship);
        RotateCommand rotate = new RotateCommand(rotateAdapter);

        assertThrows(UnsupportedOperationException.class, rotate::execute);
    }

    @Test
    void errorGettingDirectionSectionsTest() {
        UObject ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("rotable", true);
        ship.setProperty("position", new Vector(12, 5));
        ship.setProperty("velocity", new Vector(-7, 3));
        ship.setProperty("angularVelocity", -1);
        ship.setProperty("direction", new Direction());

        RotateAdapter rotateAdapter = new RotateAdapter(ship);
        RotateCommand rotate = new RotateCommand(rotateAdapter);

        assertThrows(UnsupportedOperationException.class, rotate::execute);
    }

    @Test
    void errorGettingDirectionTest() {
        UObject ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("rotable", true);
        ship.setProperty("position", new Vector(12, 5));
        ship.setProperty("velocity", new Vector(-7, 3));
        ship.setProperty("angularVelocity", -1);
        ship.setProperty("directionSections", 8);

        RotateAdapter rotateAdapter = new RotateAdapter(ship);
        RotateCommand rotate = new RotateCommand(rotateAdapter);

        assertThrows(UnsupportedOperationException.class, rotate::execute);
    }

    @Test
    void errorSettingDirectionTest() {
        UObject ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("rotable", false);
        ship.setProperty("position", new Vector(12, 5));
        ship.setProperty("velocity", new Vector(-7, 3));
        ship.setProperty("angularVelocity", -1);
        ship.setProperty("directionSections", 8);
        ship.setProperty("direction", new Direction());

        RotateAdapter rotateAdapter = new RotateAdapter(ship);
        RotateCommand rotate = new RotateCommand(rotateAdapter);

        assertThrows(UnsupportedOperationException.class, rotate::execute);
    }
}

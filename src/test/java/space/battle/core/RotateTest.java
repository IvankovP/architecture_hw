package space.battle.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.battle.core.adapter.VelocityAdapter;
import space.battle.core.command.action.ChangeVelocityCommand;
import space.battle.core.entity.Ship;
import space.battle.core.entity.UObject;
import space.battle.core.command.action.RotateCommand;
import space.battle.core.support.Direction;
import space.battle.core.adapter.RotateAdapter;
import space.battle.core.support.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RotateTest {

    private UObject ship;

    @BeforeEach
    void before() {
        ship = new Ship();
        ship.setProperty("movable", true);
        ship.setProperty("rotable", true);
        ship.setProperty("position", new Vector(12, 5));
        ship.setProperty("velocity", new Vector(-7, 3));
    }

    @Test
    void rotateTest() {
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
        ship.setProperty("directionSections", 8);
        ship.setProperty("direction", new Direction());

        RotateAdapter rotateAdapter = new RotateAdapter(ship);
        RotateCommand rotate = new RotateCommand(rotateAdapter);

        assertThrows(UnsupportedOperationException.class, rotate::execute);
    }

    @Test
    void errorGettingDirectionSectionsTest() {
        ship.setProperty("angularVelocity", -1);
        ship.setProperty("direction", new Direction());

        RotateAdapter rotateAdapter = new RotateAdapter(ship);
        RotateCommand rotate = new RotateCommand(rotateAdapter);

        assertThrows(UnsupportedOperationException.class, rotate::execute);
    }

    @Test
    void errorGettingDirectionTest() {
        ship.setProperty("angularVelocity", -1);
        ship.setProperty("directionSections", 8);

        RotateAdapter rotateAdapter = new RotateAdapter(ship);
        RotateCommand rotate = new RotateCommand(rotateAdapter);

        assertThrows(UnsupportedOperationException.class, rotate::execute);
    }

    @Test
    void errorSettingDirectionTest() {
        ship.setProperty("rotable", false);
        ship.setProperty("angularVelocity", -1);
        ship.setProperty("directionSections", 8);
        ship.setProperty("direction", new Direction());

        RotateAdapter rotateAdapter = new RotateAdapter(ship);
        RotateCommand rotate = new RotateCommand(rotateAdapter);

        assertThrows(UnsupportedOperationException.class, rotate::execute);
    }

    @Test
    void changeVelocityVectorTest() {
        ship.setProperty("velocity", new Vector(5, 0));
        ship.setProperty("angularVelocity", -1);
        ship.setProperty("directionSections", 8);
        ship.setProperty("direction", new Direction());

        RotateAdapter rotateAdapter = new RotateAdapter(ship);
        RotateCommand rotate = new RotateCommand(rotateAdapter);
        rotate.execute();

        VelocityAdapter velocityAdapter = new VelocityAdapter(ship);
        ChangeVelocityCommand changeVelocityCommand = new ChangeVelocityCommand(velocityAdapter);
        changeVelocityCommand.execute();

        assertEquals(-1, ((Direction) ship.getProperty("direction")).getDirection());
        assertEquals(new Vector(3, 4), ship.getProperty("velocity"));
    }

    @Test
    void changeVelocityVectorNoRotateTest() {
        ship.setProperty("velocity", new Vector(5, 0));
        ship.setProperty("angularVelocity", -1);
        ship.setProperty("directionSections", 8);
        ship.setProperty("direction", new Direction());

        VelocityAdapter velocityAdapter = new VelocityAdapter(ship);
        ChangeVelocityCommand changeVelocityCommand = new ChangeVelocityCommand(velocityAdapter);
        changeVelocityCommand.execute();

        assertEquals(0, ((Direction) ship.getProperty("direction")).getDirection());
        assertEquals(new Vector(5, 0), ship.getProperty("velocity"));
    }
}

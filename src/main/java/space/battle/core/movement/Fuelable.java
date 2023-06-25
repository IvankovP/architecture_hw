package space.battle.core.movement;

public interface Fuelable {
    void checkFuel();

    int getFuel();

    void burnFuel();
}

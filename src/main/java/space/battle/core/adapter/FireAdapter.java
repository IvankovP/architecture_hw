package space.battle.core.adapter;

import space.battle.core.entity.UObject;
import space.battle.core.exception.CommandException;
import space.battle.core.movement.Fierlable;

public class FireAdapter implements Fierlable {

    private final UObject fierlableObject;

    public FireAdapter(UObject fierlableObject) {
        this.fierlableObject = fierlableObject;
    }

    private Object getProperty(String name) {
        Object property = fierlableObject.getProperty(name);
        if (property != null) {
            return property;
        }
        throw new CommandException("Error get parameter " + name);
    }

    @Override
    public void fire() {
        int count = (int) getProperty("countRockets");
        if (count <= 0) {
            return;
        }
        System.out.println("Fire!!!");
        fierlableObject.setProperty("countRockets", --count);
    }
}

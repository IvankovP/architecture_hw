package space.battle.core;

import space.battle.core.entity.UObject;

import java.util.HashSet;
import java.util.Set;

public class Scope {
    private final Set<UObject> uObjectSet = new HashSet<>();
    private final String userName;

    public Scope(String userName) {
        this.userName = userName;
    }

    public Set<UObject> getUObjectSet() {
        return uObjectSet;
    }

    public String getUserName() {
        return userName;
    }
}

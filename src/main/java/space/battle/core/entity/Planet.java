package space.battle.core.entity;

import java.util.HashMap;
import java.util.Map;

public class Planet implements UObject {

    private final Map<String, Object> properties = new HashMap<>();

    @Override
    public void setProperty(String key, Object newValue) {
        properties.put(key, newValue);
    }

    @Override
    public Object getProperty(String key) {
        return properties.get(key);
    }
}

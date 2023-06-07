package space.battle.core;

import java.util.HashMap;
import java.util.Map;

public class IoC {

    private static final Map<String, Object> objects = new HashMap<>();

    public IoC() {
        throw new UnsupportedOperationException();
    }

    public static void register(String entityId, String id, Object object) {
        objects.put(getKey(entityId, id), object);
    }

    public static void register(String entityId, Object object) {
        objects.put(getKey(entityId, null), object);
    }

    private static String getKey(String entityId, String id) {
        if (id == null) {
            return entityId.toLowerCase();
        }
        return entityId.concat(id).toLowerCase();
    }

    public static <T> T resolve(String entityId) {
        return resolve(entityId, "");
    }

    public static <T> T resolve(String entityId, String id) {
        String key = getKey(entityId, id);
        if (!objects.containsKey(key)) {
            return null;
        }
        return (T) objects.get(key);
    }
}

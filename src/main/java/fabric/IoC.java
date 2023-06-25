package fabric;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class IoC<R> {

    private final Map<String, Function<Object[], R>> objects = new HashMap<>();

    public void resolve(String key, Function<Object[], R> function) {
        objects.put(key.toLowerCase(), function);
    }

    public R resolve(String key, Object[] args) {
        String keyLowerCase = key.toLowerCase();
        if (!objects.containsKey(keyLowerCase)) {
            return null;
        }
        return objects.get(keyLowerCase).apply(args);
    }

    public R resolve(String key) {
        return resolve(key, new Object[0]);
    }
}

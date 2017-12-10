package dm.otus.serialization;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class ReflectionHelper {
    public static Map<String, Object> GetObjectFields(Object object) {
        HashMap<String, Object> res = new HashMap<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            res.put(field.getName(), value);
        }
        return res;
    }
}

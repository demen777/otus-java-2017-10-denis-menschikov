package dm.otus.serialization;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
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

    static Object[] castPrimitiveArrayToObjectArray(Object object) {
        Object[] objectArray = new Object[Array.getLength(object)];
        int length = Array.getLength(object);
        for (int i = 0; i < length; i ++) {
            objectArray[i] = Array.get(object, i);
        }
        return objectArray;
    }

    static ValueType recognizeValueType(Object object) {
        if (object == null) return ValueType.NULL;
        if (object instanceof Boolean) return ValueType.BOOLEAN;
        if (object instanceof String) return ValueType.STRING;
        if (Number.class.isAssignableFrom(object.getClass())) return ValueType.NUMBER;
        if (object instanceof Collection) return ValueType.COLLECTION;
        if (object.getClass().isArray()) return ValueType.ARRAY;
        if (object instanceof Map) return ValueType.MAP;
        return ValueType.OBJECT;
    }

    enum ValueType {
        NULL,
        COLLECTION,
        ARRAY,
        OBJECT,
        STRING,
        BOOLEAN,
        NUMBER,
        MAP
    }
}

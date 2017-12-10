package dm.otus.serialization;

import org.apache.commons.lang3.ArrayUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import java.util.*;

@SuppressWarnings("unchecked")
public class JsonHelper {

    enum ValueType {
        NULL,
        COLLECTION,
        ARRAY,
        VALUE,
        OBJECT,
        MAP
    }

    public static String toJsonString(Object object) {
        return Objects.requireNonNull(toJson(object)).toJSONString();
    }

    public static JSONAware toJson(Object object) {
        final ValueType objectValueType = recognizeValueType(object);
        switch(objectValueType) {
            case NULL:
            case VALUE:
                final JSONArray resValue = new JSONArray();
                resValue.add(object);
                return resValue;
            case ARRAY:
                Object[] objectArray = null;
                if (object instanceof long[]) objectArray = ArrayUtils.toObject((long[])object);
                if (object instanceof int[]) objectArray = ArrayUtils.toObject((int[])object);
                if (object instanceof short[]) objectArray = ArrayUtils.toObject((short[])object);
                if (object instanceof byte[]) objectArray = ArrayUtils.toObject((byte[])object);
                if (object instanceof boolean[]) objectArray = ArrayUtils.toObject((boolean[])object);
                if (object instanceof char[]) objectArray = ArrayUtils.toObject((char[])object);
                if (object instanceof float[]) objectArray = ArrayUtils.toObject((float[])object);
                if (object instanceof double[]) objectArray = ArrayUtils.toObject((double[])object);
                List list = Arrays.asList(Objects.requireNonNull(objectArray));
                return getJsonFromCollection(list);
            case COLLECTION:
                return getJsonFromCollection((Collection) object);
            case MAP:
                final Map map = (Map)object;
                return getJsonFromMap(map);
            case OBJECT:
                final Map objectFields = ReflectionHelper.GetObjectFields(object);
                return getJsonFromMap(objectFields);
        }
        return null;
    }

    private static JSONAware getJsonFromMap(Map<?,?> objectFields) {
        final JSONObject jsonObject = new JSONObject();
        for(Map.Entry<?,?> entry:objectFields.entrySet()) {
            Object value = entry.getValue();
            jsonObject.put(entry.getKey(), getJsonValue(value));
        }
        return jsonObject;
    }

    private static Object getJsonValue(Object value) {
        Object forMapValue = null;
        switch(recognizeValueType(value)) {
            case NULL:
            case VALUE:
                forMapValue = value;
                break;
            case COLLECTION:
            case ARRAY:
            case OBJECT:
            case MAP:
                forMapValue = toJson(value);
        }
        return forMapValue;
    }

    private static JSONArray getJsonFromCollection(Collection object) {
        final JSONArray resCollection = new JSONArray();
        for(Object element: object) {
            resCollection.add(getJsonValue(element));
        }
        return resCollection;
    }

    private static ValueType recognizeValueType(Object object) {
        if (object == null) return ValueType.NULL;
        if ((object instanceof Boolean) || (object instanceof String)
                || (Number.class.isAssignableFrom(object.getClass()))) return ValueType.VALUE;
        if (object instanceof Collection) return ValueType.COLLECTION;
        if (object.getClass().isArray()) return ValueType.ARRAY;
        if (object instanceof Map) return ValueType.MAP;
        return ValueType.OBJECT;
    }

}

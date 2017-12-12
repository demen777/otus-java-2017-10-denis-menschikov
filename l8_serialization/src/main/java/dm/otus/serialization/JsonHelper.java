package dm.otus.serialization;

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

@SuppressWarnings("unchecked")
public class JsonHelper {

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

    public static String toJsonString(Object object) {
        final ValueType objectValueType = recognizeValueType(object);
        switch(objectValueType) {
            case NULL:
            case NUMBER:
            case BOOLEAN:
            case STRING:
                return String.format("[%s]", getJsonValue(object));
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

    private static String getJsonFromMap(Map<?,?> objectFields) {
        StringJoiner joiner = new StringJoiner(",");
        for(Map.Entry<?,?> entry:objectFields.entrySet()) {
            Object value = entry.getValue();
            joiner.add(String.format("\"%s\":%s", entry.getKey(), getJsonValue(value)));
        }
        return String.format("{%s}", joiner.toString());
    }

    private static String getJsonValue(Object value) {
        String forMapValue = null;
        switch(recognizeValueType(value)) {
            case NULL:
                forMapValue = "null";
                break;
            case BOOLEAN:
            case NUMBER:
                forMapValue = value.toString();
                break;
            case STRING:
                forMapValue = String.format("\"%s\"", value);
                break;
            case COLLECTION:
            case ARRAY:
            case OBJECT:
            case MAP:
                forMapValue = toJsonString(value);
                break;
        }
        return forMapValue;
    }

    private static String getJsonFromCollection(Collection object) {
        StringJoiner joiner = new StringJoiner(",");
        for(Object element: object) {
            joiner.add(getJsonValue(element));
        }
        return String.format("[%s]", joiner.toString());
    }

    private static ValueType recognizeValueType(Object object) {
        if (object == null) return ValueType.NULL;
        if (object instanceof Boolean) return ValueType.BOOLEAN;
        if (object instanceof String) return ValueType.STRING;
        if (Number.class.isAssignableFrom(object.getClass())) return ValueType.NUMBER;
        if (object instanceof Collection) return ValueType.COLLECTION;
        if (object.getClass().isArray()) return ValueType.ARRAY;
        if (object instanceof Map) return ValueType.MAP;
        return ValueType.OBJECT;
    }

}

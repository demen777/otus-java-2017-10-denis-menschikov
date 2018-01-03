package dm.otus.serialization;

import java.util.*;

@SuppressWarnings({"unchecked", "WeakerAccess"})
public class JsonHelper {

    public static String toJsonString(Object object) {
        final ReflectionHelper.ValueType objectValueType = ReflectionHelper.recognizeValueType(object);
        switch(objectValueType) {
            case NULL:
            case NUMBER:
            case BOOLEAN:
            case STRING:
                return String.format("[%s]", getJsonValue(object));
            case ARRAY:
                Object[] objectArray = ReflectionHelper.castPrimitiveArrayToObjectArray(object);
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
        switch(ReflectionHelper.recognizeValueType(value)) {
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

}

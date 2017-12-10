package dm.otus.serialization;

import com.google.gson.Gson;
import org.json.simple.JSONAware;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.*;

@SuppressWarnings("FieldCanBeLocal")
class TestClass {
    private final int integer = 1;
    private final String string = "Hello JSON";
    private final boolean bool = true;
    private final double double_ = 0.5;
    private final long[] longArray = {1L, 2L, 4L};
    private final List list;
    private final TestClass anotherTestClass;
    private final Map map;

    TestClass(List list, TestClass anotherTestClass, Map map) {
        this.list = list;
        this.anotherTestClass = anotherTestClass;
        this.map = map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestClass testClass = (TestClass) o;
        return Double.compare(testClass.double_, double_) == 0
                && Objects.equals(string, testClass.string)
                && Arrays.equals(longArray, testClass.longArray)
                && Objects.equals(list, testClass.list)
                && Objects.equals(anotherTestClass, testClass.anotherTestClass)
                && Objects.equals(map, testClass.map);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(integer, string, bool, double_, list, anotherTestClass, map);
        result = 31 * result + Arrays.hashCode(longArray);
        return result;
    }
}

@SuppressWarnings("unchecked")
class JsonHelperTest {

    @Test
    void toJson_Object() {
        TestClass testClass = createTestClass();
        String jsonString = JsonHelper.toJsonString(testClass);
        System.out.println(jsonString);
        Gson gson = new Gson();
        TestClass testClassFromJson = gson.fromJson(jsonString, TestClass.class);
        Assert.assertTrue(testClass.equals(testClassFromJson));
    }


    @Test
    void toJson_UpperObject() {
        TestClass testClass = createTestClass();
        List list2 = new ArrayList();
        list2.add(true);
        list2.add("Not object");
        Map map2 = new HashMap();
        map2.put("firstKey", "Not object 2");
        map2.put("secondKey", 6.5);
        TestClass upperTestClass = new TestClass(list2, testClass, map2);
        String jsonString = JsonHelper.toJsonString(upperTestClass);
        System.out.println(jsonString);
        Gson gson = new Gson();
        TestClass testClassFromJson = gson.fromJson(jsonString, TestClass.class);
        Assert.assertTrue(upperTestClass.equals(testClassFromJson));
    }

    @Test
    void toJson_Map() {
        Map map1 = new HashMap();
        map1.put("firstKey", "Hello JSON from map1");
        map1.put("secondKey", 5L);
        String jsonString = JsonHelper.toJsonString(map1);
        Assert.assertEquals("{\"firstKey\":\"Hello JSON from map1\",\"secondKey\":5}", jsonString);
    }

    @Test
    void toJson_int() {
        Assert.assertEquals("[1]", JsonHelper.toJsonString(1));
    }

    @Test
    void toJson_double() {
        Assert.assertEquals("[5.5]", JsonHelper.toJsonString(5.5));
    }

    @Test
    void toJson_null() {
        Assert.assertEquals("[null]", JsonHelper.toJsonString(null));
    }

    private TestClass createTestClass() {
        List list1 = new ArrayList();
        list1.add(1.1);
        list1.add("Hello JSON from list1");
        Map map1 = new HashMap();
        map1.put("firstKey", "Hello JSON from map1");
        map1.put("secondKey", 5.6);
        return new TestClass(list1, null, map1);
    }
}
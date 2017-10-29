package dm.otus.l3_collections;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Objects;


@SuppressWarnings({"Java8ListSort", "MismatchedQueryAndUpdateOfCollection", "MismatchedReadAndWriteOfArray"})
class MyArrayListTest {

    @Test
    void addAllTest() {
        MyArrayList<Integer> list = new MyArrayList<>();
        java.util.Collections.addAll(list, 2, 4);
        java.util.Collections.addAll(list, 6, 8, 10, 12, 14, 16, 18, 20, 22);
        final Integer[] expectedArray = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22};
        assertArrayEquals(list.toArray(), expectedArray);
    }

    private int reverseCompare(Integer x, Integer y){
        return (x < y) ? 1 : ((Objects.equals(x, y)) ? 0 : -1);
    }

    @Test
    void sortTestCommon() {
        MyArrayList<Integer> list = new MyArrayList<>();
        java.util.Collections.addAll(list, 22, 6, 8, 2, 12, 14, 4, 16, 10, 18, 20);
        java.util.Collections.sort(list, this::reverseCompare);
        final Integer[] expectedArray = {22, 20, 18, 16, 14, 12, 10, 8, 6, 4, 2};
        assertArrayEquals(list.toArray(), expectedArray);
    }

    @Test
    void sortTestZeroLength() {
        MyArrayList<Integer> list = new MyArrayList<>();
        java.util.Collections.sort(list);
        final Integer[] expectedArray = {};
        assertArrayEquals(list.toArray(), expectedArray);
    }

    @Test
    void copyTest() {
        MyArrayList<String> src = new MyArrayList<>();
        MyArrayList<String> dst = new MyArrayList<>();
        java.util.Collections.addAll(src, "one", "two", "three", "four", "five");
        java.util.Collections.addAll(dst, "1", "2", "3", "4", "5", "6", "7");
        java.util.Collections.copy(dst, src);
        final String[] expectedArray = {"one", "two", "three", "four", "five", "6", "7"};
        assertArrayEquals(expectedArray, dst.toArray());
    }

    @Test
    void copyTestZeroSrc() {
        MyArrayList<String> src = new MyArrayList<>();
        MyArrayList<String> dst = new MyArrayList<>();
        java.util.Collections.addAll(dst, "1", "2", "3", "4", "5", "6", "7");
        java.util.Collections.copy(dst, src);
        final String[] expectedArray = {"1", "2", "3", "4", "5", "6", "7"};
        assertArrayEquals(expectedArray, dst.toArray());
    }

}
package denis_menschikov.otus;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("SameParameterValue")
class ArraySorterTest {

    @Test
    void sort() {
        Integer[] integerArray = generateRandomArray(1000);
        ArraySorter<Integer> sorter = new MultiThreadArraySorter<>(4);
        Integer[] sortedArray = sorter.sort(integerArray);
        Arrays.sort(integerArray);
        assertArrayEquals(integerArray, sortedArray);
    }

    private Integer[] generateRandomArray(int size) {
        Integer[] res = new Integer[size];
        Random random = new Random();
        for(int i=0; i<size; i++) {
            res[i] = random.nextInt(size);
        }
        return res;
    }
}
package denis_menschikov.otus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiThreadArraySorter<T extends Comparable> implements ArraySorter<T> {

    private final int numberOfThread;

    public MultiThreadArraySorter(int numberOfThread) {
        this.numberOfThread = numberOfThread;
    }

    @Override
    public T[] sort(T[] array) {
        Thread[] sortWorkers = new Thread[numberOfThread];
        List<T[]> arrayParts = new ArrayList<>();
        for(int i=0; i<numberOfThread; i++) {
            T[] arrayPart = cloneArrayPart(array, i);
            arrayParts.add(arrayPart);
            sortWorkers[i] = new Thread(new SortWorker<>(arrayPart));
            sortWorkers[i].start();
        }
        for(int i=0; i<numberOfThread; i++) {
            try {
                sortWorkers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return sortArrayPart(arrayParts);
    }

    private T[] sortArrayPart(List<T[]> arrayParts) {
        int size = arrayParts.stream().mapToInt(l -> l.length).sum();
        @SuppressWarnings("unchecked")
        T[] res = (T[]) Array.newInstance(arrayParts.get(0)[0].getClass(), size);
        int[] positions = new int[numberOfThread];
        int curIndex = 0;
        while(curIndex<size) {
            T curValue = null;
            int selectedPart = -1;
            for(int i=0; i<numberOfThread; i++) {
                if (positions[i] < arrayParts.get(i).length) {
                    T partValue = arrayParts.get(i)[positions[i]];
                    //noinspection unchecked
                    if (curValue == null || curValue.compareTo(partValue) > 0) {
                        curValue = partValue;
                        selectedPart = i;
                    }
                }
            }
            res[curIndex++] = curValue;
            positions[selectedPart]++;
        }
        return res;
    }

    private T[] cloneArrayPart(T[] array, int i) {
        int sourcePosition = i*(array.length/numberOfThread);
        int size = (i==numberOfThread-1)
                ? array.length - array.length/numberOfThread*i
                : array.length/numberOfThread;
        T[] part = Arrays.copyOfRange(array, sourcePosition, sourcePosition+size);
        return part;
    }
}

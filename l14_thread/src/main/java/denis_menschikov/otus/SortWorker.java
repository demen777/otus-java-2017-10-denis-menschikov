package denis_menschikov.otus;

import java.util.Arrays;

class SortWorker<T> implements Runnable {

    private final T[] array;

    public SortWorker(T[] array) {
        this.array = array;
    }

    @Override
    public void run() {
        Arrays.sort(array);
    }
}

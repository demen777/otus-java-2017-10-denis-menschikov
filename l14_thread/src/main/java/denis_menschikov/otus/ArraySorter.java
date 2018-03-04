package denis_menschikov.otus;

@SuppressWarnings("WeakerAccess")
public interface ArraySorter<T extends Comparable> {
    T[] sort(T[] array);
}

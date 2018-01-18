package dm.otus.l10_hibernate;

@SuppressWarnings("WeakerAccess")
public interface Cache<K,V> {
    void put(K key, V value);
    V get(K key);
    long getHitsCount();
    long getMissCount();
    void dispose();
}

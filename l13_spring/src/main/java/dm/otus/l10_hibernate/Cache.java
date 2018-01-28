package dm.otus.l10_hibernate;

@SuppressWarnings("WeakerAccess")
public interface Cache<K,V> extends CacheInfo {
    void put(K key, V value);
    V get(K key);
    void dispose();
}

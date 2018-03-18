package dm.otus.l15_msg.db;

@SuppressWarnings("WeakerAccess")
public interface Cache<K,V> extends CacheInfo {
    void put(K key, V value);
    V get(K key);
    void dispose();
}

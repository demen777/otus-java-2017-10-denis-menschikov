package dm.otus.l15_msg.cache;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;


public class CacheImpl<K,V> implements Cache<K,V> {
    private final int maxSize;
    private final long lifeTime;
    private final long idleTime;
    private final LinkedHashMap<K, SoftReference<Element>> map;
    private final ReferenceQueue<Element> deletedElements;
    private long hitsCounter;
    private long missCounter;
    private final Timer timer = new Timer();

    public CacheImpl(int maxSize, long lifeTime, long idleTime) {
        this.map = new LinkedHashMap<>();
        this.maxSize = maxSize;
        this.hitsCounter = 0;
        this.missCounter = 0;
        this.lifeTime = lifeTime;
        this.idleTime = idleTime;
        this.deletedElements = new ReferenceQueue<>();
    }

    @Override
    public void put(K key, V value) {
        if (map.containsKey(key)) {
            map.remove(key);
        }
        else {
            if (map.size() >= maxSize) {
                removeDeletedReference();
            }
            if (map.size() >= maxSize) {
                removeOldest();
            }
        }
        Element element = new Element(key, value);
        map.put(key, new SoftReference<>(element, deletedElements));
        if (lifeTime != 0) {
            addLifeTimeTimer(key);
        }
        if (idleTime != 0) {
            addIdleTimeTimer(key);
        }
    }

    private void addIdleTimeTimer(K key) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Element element = internalGet(key);
                if (element != null) {
                    if (element.getLastAccessTime() + idleTime >= System.currentTimeMillis()) {
                        return;
                    }
                    else {
                        map.remove(key);
                    }
                }
                this.cancel();
            }
        };
        timer.schedule(timerTask, idleTime, idleTime);
    }

    private void addLifeTimeTimer(K key) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Element element = internalGet(key);
                if (element != null && element.getCreationTime() + lifeTime <= System.currentTimeMillis()) {
                        map.remove(key);
                }
                this.cancel();
            }
        };
        timer.schedule(timerTask, lifeTime);
    }

    private void removeOldest() {
        K firstKey = map.keySet().iterator().next();
        map.remove(firstKey);
    }

    private void removeDeletedReference() {
        try {
            while (deletedElements.poll() != null) {
                    Reference<? extends Element> reference = deletedElements.remove(1);
                    Element element = reference.get();
                    if (element !=  null) {
                        map.remove(element.getKey());
                    }
            }
        }
        catch (InterruptedException ignored) {
        }
    }

    private Element internalGet(K key) {
        SoftReference<Element> reference = map.get(key);
        if (reference == null) {
            return null;
        }
        return reference.get();
    }

    @Override
    public V get(K key) {
        Element element = internalGet(key);
        if(element != null) {
            hitsCounter++;
            return element.getValue();
        }
        else {
            missCounter++;
            return null;
        }
    }

    @Override
    public long getHitCount() {
        return hitsCounter;
    }

    @Override
    public long getMissCount() {
        return missCounter;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private class Element {
        private final K key;
        private final V value;
        private final long creationTime;
        private long lastAccessTime;

        Element(K key, V value) {
            this.key = key;
            this.value = value;
            this.creationTime = System.currentTimeMillis();
            this.lastAccessTime = System.currentTimeMillis();
        }

        K getKey() {
            return key;
        }

        V getValue() {
            lastAccessTime = System.currentTimeMillis();
            return value;
        }

        long getCreationTime() {
            return creationTime;
        }

        long getLastAccessTime() {
            return lastAccessTime;
        }
    }
}

package dm.otus.l10_hibernate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CacheTest {
    Cache<Long, String> cache;

    @Test
    void checkMaxSize() {
        cache = new CacheImpl<>(2, 0, 0);
        cache.put(1L, "111");
        cache.put(2L, "222");
        assertEquals("111", cache.get(1L));
        cache.put(3L, "333");
        assertNull(cache.get(1L));
        assertEquals("222", cache.get(2L));
        assertEquals("333", cache.get(3L));
    }

    @Test
    void checkLifeTime() throws InterruptedException {
        cache = new CacheImpl<>(200, 1000, 0);
        cache.put(1L, "111");
        assertEquals("111", cache.get(1L));
        Thread.sleep(1100);
        assertNull(cache.get(1L));
    }

    @Test
    void checkIdleTime() throws InterruptedException {
        cache = new CacheImpl<>(200, 0, 1000);
        cache.put(1L, "111");
        assertEquals("111", cache.get(1L));
        Thread.sleep(500);
        assertEquals("111", cache.get(1L));
        Thread.sleep(700);
        assertEquals("111", cache.get(1L));
        Thread.sleep(2000);
        assertNull(cache.get(1L));
    }

    @AfterEach
    void tearDown() {
        cache.dispose();
    }
}